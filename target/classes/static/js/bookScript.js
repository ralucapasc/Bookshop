$(document).ready(function () {
    $(".btn-cart").click(addToCart);

    $("body").on('click', '.btn-remove-item', removeItem);

    $("div").on('click', 'clear-all-action', clearAll);

    $("form").on('click', '.update-quantity', updateQuantity);
});

function addToCart(event) {
    event.preventDefault();
    var url = event.target.getAttribute('href');
    var qty = $('#qty').val();

    $.post(url, {'qty': qty}).done(
        function (response) {
            if (qty != ("^(0|[1-9][0-9]*)$") && qty > 0) {
                $("#rightSidebar").html(response);
            }
            if (qty < 0) {
                alert("The value is negative!");
            }
        }
    ).fail(function (data) {
        alert("The value is invalid!")
    });

}

function removeItem(event) {
    event.preventDefault();
    var url = this.getAttribute('href');

    $.post(url).done(
        function (response) {
            $("#rightSidebar").html(response);
            $("#form-cart").html(response);
        }).fail(function (data) {
    });
}

function clearAll(event) {
    event.preventDefault();
    var url = event.target.getAttribute('href');

    console.log(url);
}

function updateQuantity(event) {
    event.preventDefault();
    var url = this.getAttribute('href');
    var books = [];

    $('tr.odd').each(function (index, tr) {
        var bookId = $(tr).find(".qty").data('value');
        var qty = $(tr).find(".qty").val();
        var bookQuantity = $(tr).find(".qty").data('quantity');
        if (qty > bookQuantity) {
            alert("We have only " + bookQuantity + " books in stock!!! ");
        } else {
            books.push({book: {id: bookId}, qty: qty});
        }
    });

    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(books),
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            alert("Success update");
            $("#form-cart").html(response)
        },
        error: function (response) {
            alert("Error update");
        }
    });
}


