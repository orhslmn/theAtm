$(document).ready(function () {
const accountId = localStorage.getItem("accountId");
    if (!accountId) {
        alert("Please log in!");
        window.location.href = "login.html";
        return;
    }

    $('#createForm').submit(function (e) {
        e.preventDefault();

        const accountData = {
            fullName: $('#fullName').val(),
            cardNumber: $('#cardNumber').val(),
            balance: parseFloat($('#balance').val())
        };
        $.ajax({
            url: '/api/account/create',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(accountData),
            success: function (response) {
                alert("Account created! ID: " + response.id);
             },
            error: function () {
                alert("Account creation failed.");
            }
        });
    });

    $('#depositForm').submit(function (e) {
        e.preventDefault();
        const id = $('#depositId').val();
        const amount = $('#depositAmount').val();

        $.post(`/api/account/deposit?id=${id}&amount=${amount}`, function (data) {
            alert("New balance: " + data.balance + " USD");
        }).fail(function () {
            alert("Deposit failed!");
        });
    });

    $('#withdrawForm').submit(function (e) {
        e.preventDefault();
        const id = $('#withdrawId').val();
        const amount = $('#withdrawAmount').val();

        $.post(`/api/account/withdraw?id=${id}&amount=${amount}`, function (data) {
            alert("New balance: " + data.balance + " USD");
        }).fail(function () {
            alert("Withdrawal failed! There might be insufficient balance.");
        });
    });

});
$('#transferForm').submit(function (e) {
    e.preventDefault();

    const senderId = $('#senderId').val();
    const receiverId = $('#receiverId').val();
    const transferAmount = $('#transferAmount').val();

$.post(`/api/account/transfer/${senderId}/${receiverId}/${transferAmount}`, function (transferData) {
    alert(transferAmount + " USD transferred. New balance: " + transferData.balance+" USD");
}).fail(function () {
    alert("Transfer failed! There might be insufficient balance.");
});
});

function getBalance() {
    const accountId = $('#balanceAccountId').val();

    $.ajax({
        url: '/api/account/balance/' + accountId,
        method: 'GET',
        success: function (balance) {
            $('#balanceResult').text("Balance: " + balance + " USD");
        },
        error: function () {
            alert("Failed to retrieve balance.");
        }
    });

  }


