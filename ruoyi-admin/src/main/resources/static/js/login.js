// 验证码图片点击刷新
document.getElementById('captchaImage').addEventListener('click', function() {
    // 这里使用随机参数来刷新图片缓存
    this.src = 'https://picsum.photos/100/40?' + Math.random();
});

// 表单验证
function validateForm(event) {
    event.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const captcha = document.getElementById('captcha').value;

    // 这里添加你的验证逻辑
    if (username.trim() === '') {
        alert('请输入用户名');
        return false;
    }

    if (password.length < 6) {
        alert('密码长度不能少于6位');
        return false;
    }

    if (captcha.length !== 4) {
        alert('请输入4位验证码');
        return false;
    }

    // 这里可以添加登录请求
    console.log('登录信息：', {
        username,
        password,
        captcha
    });

    // 模拟登录成功
    alert('登录成功！');
    return false;
} 