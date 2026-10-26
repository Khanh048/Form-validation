# Form Validation

Ứng dụng Android minh hoạ xử lý sự kiện và kiểm tra dữ liệu form đăng ký theo **Lecture 5 – Events & Validation**.

## Chức năng

- Xây dựng `User` data class.
- Form đăng ký gồm họ tên, email, mật khẩu, số điện thoại và điều khoản sử dụng.
- Kiểm tra tất cả trường cùng lúc bằng `FormValidator`.
- Hiển thị lỗi tại từng trường và `Snackbar` khi đăng ký thất bại.
- Chuyển sang trang chủ, hiển thị thông tin an toàn của người dùng khi thành công.
- Nút **Huỷ** xoá dữ liệu và trạng thái lỗi để nhập lại.
- ViewBinding, `setOnClickListener`, `doAfterTextChanged`, Material `TextInputLayout`.

## Quy tắc kiểm tra

- Họ tên: bắt buộc, tối thiểu 2 từ.
- Email: bắt buộc, đúng định dạng email.
- Mật khẩu: bắt buộc, tối thiểu 6 ký tự.
- Điện thoại: 10 chữ số, bắt đầu bằng `03`, `05`, `07`, `08` hoặc `09`.
- Điều khoản: bắt buộc đồng ý.

## Công nghệ

- Kotlin + XML
- Android Gradle Plugin 9.2.1
- Gradle 9.6.0
- compileSdk/targetSdk 37, minSdk 24
- Material Components và ViewBinding

## Chạy ứng dụng

1. Mở thư mục project bằng Android Studio.
2. Cài Android SDK 37 nếu IDE yêu cầu.
3. Chờ Gradle Sync hoàn tất.
4. Chạy cấu hình `app` trên máy ảo hoặc thiết bị Android API 24 trở lên.

Có thể kiểm tra build từ terminal:

```shell
./gradlew assembleDebug
```

Trên Windows:

```bat
gradlew.bat assembleDebug
```

> Mật khẩu chỉ tồn tại trong đối tượng được chuyển giữa hai màn hình để mô phỏng đăng ký và không được hiển thị ở trang chủ.
