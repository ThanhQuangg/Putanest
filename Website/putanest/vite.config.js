// import { defineConfig } from "vite";
// import react from "@vitejs/plugin-react";

// export default defineConfig({
//   plugins: [react()],
//   define: {
//     "process.env": {}, // Polyfill process.env
//   },
// });

import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  base: '/', // Đảm bảo URL đúng khi deploy
  build: {
    outDir: 'dist' // Vercel sẽ lấy thư mục này để deploy
  },
  server: {
    port: 3000
  }
});

