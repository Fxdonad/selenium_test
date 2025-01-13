
# Báo cáo kiểm thử: Login và Đăng ký trên trang "https://practice.automationtesting.in"

### **Thông tin chung:**
- **Trang web kiểm thử**: [https://practice.automationtesting.in](https://practice.automationtesting.in)
- **Chức năng kiểm thử**: Đăng nhập và đăng ký
- **Môi trường kiểm thử**: Selenium WebDriver, TestNG, Maven

---

### **Kết quả kiểm thử:**
- **Test Case Passed:**
  - `testValidLogin`
- **Test Case Failed:**
  - `testInvalidLogin`
  - `testRegistrationWithExistingEmail`
  - `testRegistrationWithWeakPassword`
- **Results:**
  - ![image](https://github.com/user-attachments/assets/dedc5716-7255-4e72-8340-a8a2e578e1d6)
  - ![image](https://github.com/user-attachments/assets/9e93b05e-0052-4bfe-b1f2-6f2d4dd981c8)

---

### **Test Cases - Code:**
    @Test
    public void testValidLogin() {
        try {
            System.out.println("Running Test: Valid Login");
            driver.findElement(By.linkText("My Account")).click();
            login("toquangduc2004@gmail.com", "toquangduc2004@");

            WebElement logoutLink = waitForElement(By.linkText("Logout"), 10);
            Assert.assertTrue(logoutLink.isDisplayed(), "Logout link is not displayed.");
            System.out.println("Test Passed: Valid Login");
        } catch (Exception e) {
            System.err.println("Test Failed: Valid Login - " + e.getClass().getSimpleName() + " - " + e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void testInvalidLogin() {
        try {
            System.out.println("Running Test: Invalid Login");
            driver.findElement(By.linkText("My Account")).click();
            login("invaliduser@gmail.com", "InvalidPass");

            WebElement errorMessage = waitForElement(By.cssSelector(".woocommerce-error li"), 10);
            Assert.assertTrue(errorMessage.getText().contains("ERROR"), "Error message not displayed.");
            System.out.println("Test Passed: Invalid Login");
        } catch (Exception e) {
            System.err.println("Test Failed: Invalid Login - " + e.getClass().getSimpleName() + " - " + e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void testRegistrationWithExistingEmail() {
        try {
            System.out.println("Running Test: Registration With Existing Email");
            driver.findElement(By.linkText("My Account")).click();
            register("toquangduc2004@gmail.com", "toquangduc2004Duc@");

            WebElement errorMessage = waitForElement(By.cssSelector(".woocommerce-error li"), 10);
            Assert.assertTrue(errorMessage.getText().contains("An account is already registered with your email address."),
                    "Error message for existing email is not displayed.");
            System.out.println("Test Passed: Registration With Existing Email");
        } catch (Exception e) {
            System.err.println("Test Failed: Registration With Existing Email - " + e.getClass().getSimpleName() + " - " + e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void testRegistrationWithWeakPassword() {
        try {
            System.out.println("Running Test: Registration With Weak Password");
            driver.findElement(By.linkText("My Account")).click();
            register("newuser@gmail.com", "123");

            WebElement errorMessage = waitForElement(By.cssSelector(".woocommerce-error li"), 10);
            Assert.assertTrue(errorMessage.getText().contains("Your password is too weak"),
                    "Error message for weak password is not displayed.");
            System.out.println("Test Passed: Registration With Weak Password");
        } catch (Exception e) {
            System.err.println("Test Failed: Registration With Weak Password - " + e.getClass().getSimpleName() + " - " + e.getMessage());
            Assert.fail();
        }
    }
---

### **Phân tích chi tiết các test case:**


### **1. Test Case: testValidLogin**
- **Mục tiêu**: Kiểm tra chức năng đăng nhập hợp lệ với thông tin người dùng chính xác.
- **Dữ liệu kiểm thử**:
  - **Username**: `toquangduc2004@gmail.com`
  - **Password**: `toquangduc2004@`
- **Bước thực hiện**:
  1. Truy cập trang đăng nhập bằng cách nhấp vào **"My Account"**.
  2. Nhập email và mật khẩu hợp lệ vào các trường tương ứng.
  3. Nhấn **"Login"**.
  4. Xác minh rằng liên kết **"Logout"** hiển thị, cho thấy đăng nhập thành công.
- **Kết quả mong đợi**: Liên kết **"Logout"** hiển thị sau khi đăng nhập thành công.
- **Kết quả thực tế**: Test case này đã Passed, vì liên kết **"Logout"** đã hiển thị, chứng tỏ người dùng đã đăng nhập thành công.

**Giải thích kết quả**:
- Selenium kiểm tra sự hiện diện của liên kết **"Logout"** trên trang sau khi đăng nhập thành công.
- Sử dụng `Assert.assertTrue(logoutLink.isDisplayed())` để xác nhận kết quả.

---

### **2. Test Case: testInvalidLogin**
- **Mục tiêu**: Kiểm tra đăng nhập với thông tin không hợp lệ (email và mật khẩu sai).
- **Dữ liệu kiểm thử**:
  - **Username**: `invaliduser@gmail.com`
  - **Password**: `InvalidPass`
- **Bước thực hiện**:
  1. Truy cập trang đăng nhập.
  2. Nhập email và mật khẩu sai vào các trường tương ứng.
  3. Nhấn **"Login"**.
  4. Kiểm tra thông báo lỗi có xuất hiện hay không.
- **Kết quả mong đợi**: Một thông báo lỗi hiển thị, nói rằng thông tin đăng nhập không chính xác.
- **Kết quả thực tế**: Test case này đã Failed. Mặc dù một thông báo lỗi hiển thị, nhưng Selenium không thể xác minh chính xác thông báo đó vì có thể nó không hiển thị đúng CSS hoặc XPath không chính xác.

**Giải pháp**:
- Xác nhận rằng XPath hoặc CSS selector được sử dụng đúng để tìm thông báo lỗi.
- Điều chỉnh lại selector nếu cần thiết, kiểm tra lại giao diện trang web.
- Đảm bảo rằng `Assert.assertTrue()` kiểm tra đúng thông báo lỗi mong đợi.

---

### **3. Test Case: testRegistrationWithExistingEmail**
- **Mục tiêu**: Kiểm tra việc đăng ký với email đã tồn tại.
- **Dữ liệu kiểm thử**:
  - **Username**: `toquangduc2004@gmail.com` (email đã tồn tại trong hệ thống).
  - **Password**: `toquangduc2004Duc@`
- **Bước thực hiện**:
  1. Truy cập trang đăng ký.
  2. Nhập email đã tồn tại và mật khẩu vào các trường tương ứng.
  3. Nhấn **"Register"**.
  4. Kiểm tra thông báo lỗi xuất hiện về email đã tồn tại.
- **Kết quả mong đợi**: Thông báo lỗi phải xuất hiện, nói rằng email đã được đăng ký.
- **Kết quả thực tế**: Test case này đã Failed. Thông báo lỗi không được xác minh đúng cách, có thể vì lỗi trong việc tìm kiếm phần tử hoặc sự thay đổi giao diện.

**Giải pháp**:
- Đảm bảo rằng phần tử thông báo lỗi được tìm thấy chính xác bằng CSS selector.
- Kiểm tra lại mã nguồn của trang web để xác minh chính xác vị trí của thông báo lỗi.

---

### **4. Test Case: testRegistrationWithWeakPassword**
- **Mục tiêu**: Kiểm tra việc đăng ký với mật khẩu yếu.
- **Dữ liệu kiểm thử**:
  - **Username**: `newuser@gmail.com`
  - **Password**: `123` (mật khẩu quá yếu).
- **Bước thực hiện**:
  1. Truy cập trang đăng ký.
  2. Nhập email mới và mật khẩu yếu vào các trường tương ứng.
  3. Nhấn **"Register"**.
  4. Kiểm tra thông báo lỗi xuất hiện về mật khẩu yếu.
- **Kết quả mong đợi**: Thông báo lỗi phải xuất hiện, nói rằng mật khẩu quá yếu.
- **Kết quả thực tế**: Test case này đã Failed. Lỗi có thể xảy ra vì không xác định chính xác thông báo lỗi hoặc giao diện trang web đã thay đổi.

---

### **Tóm tắt kết quả:**

- **Test case `testValidLogin` đã Passed**: Đăng nhập hợp lệ với thông tin chính xác.
- **Các test case còn lại (`testInvalidLogin`, `testRegistrationWithExistingEmail`, `testRegistrationWithWeakPassword`) đã Failed**: Đều liên quan đến việc xác minh các thông báo lỗi hoặc các phần tử không được tìm thấy chính xác.

---

### **Link câu hỏi GPT:**
[https://chatgpt.com/share/6784f9a8-0370-800d-b052-e355c3fc3fe7]
