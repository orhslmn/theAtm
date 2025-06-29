$('#loginForm').submit(function (e) {
    e.preventDefault();
    console.log("form sumbit");
    const loginData = {
        cardNumber: $('#loginCard').val(),
        password :  $('#password').val()
    };

    $.ajax({
        url: '/api/account/login',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(loginData),
        success: function (response) {
            alert("Welcome, " + response.fullName);
            localStorage.setItem("accountId", response.id);
            window.location.href = "account.html";
        },
        error: function () {
            alert("Login failed!");
        }
    });

});