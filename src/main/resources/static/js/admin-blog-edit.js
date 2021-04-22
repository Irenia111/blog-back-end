$('.ui.dropdown').dropdown({on : 'hover'});

// 表单验证
$('#blog-form').form({
    fields: {
        title: {
            identifier: 'title',
            rules: [{
                type: 'empty',
                prompt: '标题：标题未输入'
            }]
        },
        firstPicture: {
            identifier: 'firstPicture',
            rules: [{
                type: 'empty',
                prompt: '首图：首图未输入'
            }]
        },
        typeId: {
            identifier: 'type.id',
            rules: [{
                type: 'empty',
                prompt: '分类：请输入博客分类'
            }]
        }
    }
});

//保存&发布功能 先设置published的值，再提交表单
$('#save-btn').click(function () {
    $('[name="published"]').val(false);
    $('#blog-form').submit();
});


$('#publish-btn').click(function () {
    $('[name="published"]').val(true);
    $('#blog-form').submit();
});