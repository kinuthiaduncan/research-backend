$('.menu-inner').slimScroll({
    height: 'auto'
});

$('.nav-btn').on('click', function() {
    $('.page-container').toggleClass('sbar_collapsed');
});