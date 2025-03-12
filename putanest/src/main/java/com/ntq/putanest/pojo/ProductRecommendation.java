package com.ntq.putanest.pojo;

public class ProductRecommendation {
    private Long productId;
    private Integer score;

    public ProductRecommendation(Long productId, Integer score) {
        this.productId = productId;
        this.score = score;
    }

    // Getters và Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}

