# Android-Compose-Sample
### UI Compose Sample: 
[Jetpack Compose Tutorial](https://github.com/SmartToolFactory/Jetpack-Compose-Tutorials)
### Learning Compose with:
1. [Kiến thức cơ bản về compose](https://developer.android.com/codelabs/jetpack-compose-basics?hl=vi)
2. [Tư duy trong compose](https://developer.android.com/jetpack/compose/mental-model?hl=vi)
3. [Quản lý State trong compose](https://developer.android.com/codelabs/basic-android-kotlin-compose-using-state?hl=vi)
4. [Cấu trúc ứng dụng đề xuất](https://developer.android.com/jetpack/guide?hl=vi)
5. [Phân tách dữ liệu sử dụng single source of truth](https://developer.android.com/codelabs/basic-android-kotlin-compose-add-repository?hl=vi)
#### Data Layer
>Lớp dữ liệu chịu trách nhiệm về logic nghiệp vụ của ứng dụng và việc tìm nguồn cũng như lưu dữ liệu cho ứng dụng. Lớp dữ liệu hiển thị dữ liệu cho lớp giao diện người dùng bằng cách sử dụng mẫu Luồng dữ liệu một chiều. Dữ liệu có thể đến từ nhiều nguồn, chẳng hạn như yêu cầu mạng, cơ sở dữ liệu cục bộ hoặc từ một tệp trên thiết bị.</br>
Một ứng dụng thậm chí có thể có nhiều nguồn dữ liệu. Khi được mở, ứng dụng sẽ truy xuất dữ liệu từ cơ sở dữ liệu cục bộ trên thiết bị, đây là nguồn đầu tiên. Trong khi chạy, ứng dụng gửi yêu cầu mạng đến nguồn thứ hai để truy xuất dữ liệu mới.</br>
Bằng cách đặt dữ liệu trong một lớp riêng biệt với đoạn mã giao diện người dùng, bạn có thể thay đổi nội dung trong phần này mà không lo ảnh hưởng đến các phần khác của mã ứng dụng. Phương pháp này là một phần của nguyên tắc thiết kế có tên là phân tách tính năng. Mỗi phần của mã ứng dụng tập trung vào một nhiệm vụ riêng và đóng gói hoạt động bên trong của nó khỏi các đoạn mã khác. Đóng gói là một hình thức ẩn cách hoạt động nội bộ của một đoạn mã khỏi các phần mã khác. Khi một phần mã cần tương tác với một phần mã khác, điều đó sẽ diễn ra thông qua một giao diện.</br>
Nhiệm vụ của lớp giao diện người dùng là hiển thị dữ liệu được cung cấp. Giao diện người dùng không còn truy xuất dữ liệu do đó là nhiệm vụ của lớp dữ liệu.</br>
Lớp dữ liệu được tạo thành từ một hoặc nhiều kho lưu trữ. Bản thân kho lưu trữ không chứa hoặc chứa nhiều nguồn dữ liệu.

![alt text](https://github.com/AperoVN/Android-Compose-Sample/blob/main/images/datalayer.png)
#### Event Flow

>Sự kiện sẽ thông báo cho một phần của chương trình là có điều gì đó đã xảy ra. Tất cả ứng dụng Android đều có một vòng lặp cập nhật giao diện người dùng cốt lõi như bên dưới:

![alt text](https://github.com/AperoVN/Android-Compose-Sample/blob/main/images/eventflow.png)

>* Sự kiện – Một sự kiện do người dùng hoặc một phần khác của chương trình tạo ra.
>* Trạng thái cập nhật – Trình xử lý sự kiện thay đổi trạng thái mà giao diện người dùng sử dụng.
>* Trạng thái hiển thị – Giao diện người dùng được cập nhật để hiển thị trạng thái mới.

# Component To Build App 
![alt text](https://github.com/AperoVN/Android-Compose-Sample/blob/main/images/ComponentSampleApp.png)
