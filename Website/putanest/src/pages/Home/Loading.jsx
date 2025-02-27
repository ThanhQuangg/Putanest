import React from 'react';
import '../../styles/Loading.scss';
const Loading = () => (
  <div className="loading-overlay">
    <div className="spinner"></div>
    <p>Đang tải trang web, vui lòng đợi trong giây lát...</p>
  </div>
);

export default Loading;
