from flask import Flask, request, jsonify
from sqlalchemy import create_engine, text
import pandas as pd
from sklearn.neighbors import NearestNeighbors
import numpy as np

app = Flask(__name__)

# Kết nối trực tiếp tới MySQL
DB_URI = "mysql+pymysql://root:boCPVJZDJOkEkyXqlaMQeqZzDaDnfUFr@localhost/railway"
engine = create_engine(DB_URI)


# Hàm lấy dữ liệu từ bảng user_actions
def get_user_actions(user_id):
    query = text("""
        SELECT user_id, product_id, action_type
        FROM user_actions
        WHERE user_id = :user_id
    """)
    with engine.connect() as conn:
        result = conn.execute(query, {"user_id": user_id})
        return [dict(row) for row in result]


# Mapping action_type thành điểm số (có thể điều chỉnh)
ACTION_WEIGHTS = {
    "view": 1,
    "add_to_cart": 5,
    "purchase": 10,
    "wishlist": 3
}


# Xây dựng ma trận user-product từ lịch sử hành động
def build_user_product_matrix():
    with engine.connect() as conn:
        df = pd.read_sql("SELECT user_id, product_id, action_type FROM user_actions", conn)

    if df.empty:
        return None, None

    # Tính điểm dựa trên action_type
    df['score'] = df['action_type'].map(ACTION_WEIGHTS).fillna(0)
    user_product_matrix = df.pivot_table(index='user_id', columns='product_id', values='score', aggfunc='sum',
                                         fill_value=0)

    return df, user_product_matrix


# Gợi ý sản phẩm bằng KNN (Collaborative Filtering)
def recommend_products(user_id, top_n=5):
    df, user_product_matrix = build_user_product_matrix()
    if df is None:
        return []

    if user_id not in user_product_matrix.index:
        return []

    # Áp dụng thuật toán KNN
    model = NearestNeighbors(metric='cosine', algorithm='brute')
    model.fit(user_product_matrix)

    user_vector = user_product_matrix.loc[user_id].values.reshape(1, -1)
    distances, indices = model.kneighbors(user_vector, n_neighbors=3)

    # Lấy các sản phẩm gợi ý (không bao gồm sản phẩm đã mua)
    similar_users = indices.flatten()[1:]  # Bỏ qua chính user_id
    recommendations = {}

    for similar_user in similar_users:
        similar_user_products = user_product_matrix.iloc[similar_user]
        for product_id, score in similar_user_products.items():
            if user_product_matrix.at[user_id, product_id] == 0:
                recommendations[product_id] = recommendations.get(product_id, 0) + score

    # Sắp xếp và chọn top_n sản phẩm có điểm cao nhất
    recommended_products = sorted(recommendations.items(), key=lambda x: x[1], reverse=True)[:top_n]

    return [{"product_id": product_id, "score": int(score)} for product_id, score in recommended_products]


@app.route("/recommend", methods=["POST"])
def recommend():
    data = request.json
    user_id = data.get("user_id")

    if not user_id:
        return jsonify({"error": "user_id is required"}), 400

    # Tính toán gợi ý
    recommendations = recommend_products(user_id)
    return jsonify({"user_id": user_id, "recommendations": recommendations})


@app.route("/", methods=["GET"])
def home():
    return "Welcome to Flask AI Recommendation!"


if __name__ == "__main__":
    app.run(debug=True, port=5000)
