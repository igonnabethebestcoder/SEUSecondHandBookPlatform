//用户点击头像时，弹出用户登录
var userlogIn = function()
{
    var modal = document.getElementsByClassName('usermodal')[0];    
    if (modal) {    
        modal.style.display = 'block';      
    }
}

var managerlogIn = function()
{
    var modal = document.getElementsByClassName('managermodal')[0];    
    if (modal) {    
        modal.style.display = 'block'; 
    }
}

// 获取所有的关闭按钮  
var modalCloseButtons = document.getElementsByClassName("close");  
  
// 遍历所有关闭按钮并绑定点击事件  
for (var i = 0; i < modalCloseButtons.length; i++) {  
    modalCloseButtons[i].onclick = function() {  
        var modalId = this.parentNode.parentNode.parentNode.id; // 获取当前点击的关闭按钮所在的弹窗的ID  
        var modal = document.getElementById(modalId);  
        if (modal) {  
            modal.style.display = 'none';  
        }  
    };  
}
  
// 阻止表单默认提交行为，以便你可以添加自定义的登录逻辑  
function usersubmitForm(event) {  
    const username = document.getElementById('username').value;  
    const password = document.getElementById('userpassword').value;  
  
    // 发送POST请求到后端  
    fetch('/user/login', {  
        method: 'POST',  
        headers: {  
            'Content-Type': 'application/x-www-form-urlencoded'  
        },  
        body: `userid=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`  
    })  
    .then(response => {  
        if (!response.ok) {  
            throw new Error('Network response was not ok');  
        }  
        return response.json(); // 或者 response.text() 取决于后端返回的内容类型  
    })  
    .then(data => {  
        // 在这里处理后端返回的数据  
        console.log(data.isSuccess); // 假设后端返回的是JSON格式的数据  
        if (data.isSuccess) {   //始终接收不到返回的值
            alert('登录成功');  
            // 执行登录成功后的操作，例如跳转到其他页面  

        } else {  
            alert('登录失败');  
            // 执行登录失败后的操作，例如显示错误信息  
        }  
    })  
    .catch(error => {  
        console.error('There has been a problem with your fetch operation:', error);  
    });  
}

function managersubmitForm(event) {  
    const username = document.getElementById('managername').value;  
    const password = document.getElementById('managerpassword').value;  
  
    // 发送POST请求到后端  
    fetch('/manager/login', {  
        method: 'POST',  
        headers: {  
            'Content-Type': 'application/x-www-form-urlencoded'  
        },  
        body: `managerid=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`  
    })  
    .then(response => {  
        if (!response.ok) {  
            throw new Error('Network response was not ok');  
        }  
        return response.json(); // 或者 response.text() 取决于后端返回的内容类型  
    })  
    .then(data => {  
        // 在这里处理后端返回的数据  
        console.log(data); // 假设后端返回的是JSON格式的数据  
        if (data.isSuccess) {  
            alert('登录成功');  
            // 执行登录成功后的操作，例如跳转到其他页面  
        } else {  
            alert('登录失败');  
            // 执行登录失败后的操作，例如显示错误信息  
        }  
    })  
    .catch(error => {  
        console.error('There has been a problem with your fetch operation:', error);  
    });  
}