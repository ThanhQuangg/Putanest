import React from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ element, requiredRole }) => {
    const userRole = localStorage.getItem("role"); 

    if (userRole !== requiredRole) {
        return <h2 style={{ color: "red", textAlign: "center" }}>Bạn không được phép truy cập trang này</h2>;
    }

    return element;
};

export default ProtectedRoute;
