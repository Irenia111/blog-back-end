// 表单验证
$('.ui.form').form({
    fields: {
        title: {
            identifier: 'title',
            rules: [{
                type: 'empty',
                prompt: '标题：标题未输入'
            }]
        }
    }
});

$('.message')
    .on('click', function () {
        $(this)
            .closest('.message')
            .transition('fade');
    });