# Deploy khaibut lên Railway

## 1. Chuẩn bị trên Railway

1. Tạo project mới: [railway.com/new](https://railway.com/new).
2. Thêm **MySQL**: `+ New` → **Database** → **MySQL** (hoặc dùng template MySQL).
3. Thêm **Service** cho app: `+ New` → **GitHub Repo** (hoặc **Empty** rồi deploy bằng CLI).

## 2. Kết nối MySQL với app

- Vào service **MySQL** → tab **Variables**.
- Vào service **khaibut** (app) → tab **Variables** → **Add variables from another service** → chọn MySQL.

Railway sẽ thêm các biến: `MYSQL_URL`, `MYSQLHOST`, `MYSQLPORT`, `MYSQLDATABASE`, `MYSQLUSER`, `MYSQLPASSWORD`. Ứng dụng đã cấu hình sẵn để đọc các biến này.

## 3. Deploy

- **Từ GitHub**: Đẩy code lên repo, Railway sẽ build từ **Dockerfile** và chạy.
- **Từ CLI**: Cài [Railway CLI](https://docs.railway.com/guides/cli), chạy `railway login` rồi `railway up` trong thư mục project.

## 4. Public URL

- Vào service app → **Settings** → **Networking** → **Generate Domain** để lấy URL công khai (ví dụ `https://khaibut-production.up.railway.app`).

## 5. API sau khi deploy

- `POST /api/auth/register` – đăng ký
- `POST /api/auth/login` – đăng nhập (trả về JWT)

Ghi chú: `PORT` do Railway gán tự động; app đã cấu hình `server.port=${PORT:8080}`.
