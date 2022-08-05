$('#errorNumber').on("input",function (e) {

    let value = $('#errorNumber').val()

    if (value > 1000) {
        $('#errorNumber').val(1000)
        $('#errorRange').val(10);
    }

    $('#errorRange').val(value / 100);

    if($('#seed').val() == "") {
        $('#seed').val(0)
    };

    loadSearchResult(e)
});

$('#errorRange').change(function (e) {

    let value = $('#errorRange').val();

    $('#errorNumber').val(value * 100);

    if($('#seed').val() == "") {
        $('#seed').val(0)
    };

    loadSearchResult(e)
});

$('#seed').on('input',function (e) {



    if($('#errorNumber').val() == "") {
        $('#errorNumber').val(0)
    };

    loadSearchResult(e)
});

$('#region').change(function (e) {

    if($('#errorNumber').val() == "") {
        $('#errorNumber').val(0)
    };

    if($('#seed').val() == "") {
        $('#seed').val(0)
    };

    loadSearchResult(e)
});

var fakeStatus

function loadSearchResult(e) {

    fakeStatus = e.type == 'change' || e.type == 'input' ? 'new' : 'old'

    if (e.type == 'change' || e.type == 'input' || $('.table-responsive').height() + $('.table-responsive').scrollTop() == $('.table-responsive').prop('scrollHeight')) {
        $.ajax({
            type: 'get',
            url: '/api/table',
            data: {
                region: $('#region').val(),
                seed: $('#seed').val(),
                error: $('#errorRange').val(),
                fakerParam: fakeStatus,
                index: $('tbody tr:last-child td:first-child').text() == '' || fakeStatus == 'new' ? 0 : $('tbody tr:last-child td:first-child').text()
            },
            success: function (data, e) {
                $('.table-responsive').scrollTop(0)
                fakeStatus == 'new' ? $('.new_rows').html(data) : $('.new_rows').append(data);
            },
        })
    }
}
$('.table-responsive').scroll(loadSearchResult);