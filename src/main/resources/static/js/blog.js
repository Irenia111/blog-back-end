adjustBlogColumn();
$(window).resize(adjustBlogColumn)

/*
$('.share-pop-up-button')
  .popup({
    hoverable: true,
    popup: $('.share-pop-up.popup'),
    position: 'left center'
  });
*/

$('.toc-pop-up-button')
    .popup({
        hoverable: true,
        popup: $('.toc-pop-up.popup'),
    });

$('.qr-pop-up-button')
    .popup({
        popup: $('.qr-pop-up.popup'),
        on: 'click',
        position: 'left center',
    });

// 目录，一个tocbot只能init一个，所以初始化了两个tocbot
// 但是这样留下了一个bug，每次页面切换之后，目录不能跟随滚动

tocbot_pc = Object.assign(tocbot)
tocbot_pc.init({
    // Where to render the table of contents.
    tocSelector: '.tocbot',
    // Where to grab the headings to build the table of contents.
    contentSelector: '.typo-selection',
    // Which headings to grab inside of the contentSelector element.
    headingSelector: 'h1, h2, h3',
    hasInnerContainers: true,
});


$('.toTop-button').click(() => {
    $('html, body').animate({
        scrollTop: $('body').offset().top
    }, 400);
});

const qrcode = new QRCode("qrcode", {
    text: "http://jindo.dev.naver.com/collie",
    width: 110,
    height: 110,
    colorDark: "#000000",
    colorLight: "#ffffff",
    correctLevel: QRCode.CorrectLevel.H
});

function adjustBlogColumn() {
    if ($(window).width() > 768) {
        $('.ui.sticky').sticky('refresh');
        $('#blog').removeClass('fifteen wide column').addClass('eleven wide column')
        $('#blog-title').removeClass('ui top attached segment').addClass('ui attached segment')
    } else {
        $('.ui.sticky.mobile').sticky('refresh');
        $('#blog').removeClass('eleven wide column').addClass('fifteen wide column')
        $('#blog-title').removeClass('ui attached segment').addClass('ui top attached segment')

        tocbot_mobile = Object.assign(tocbot)
        tocbot_mobile.init({
            // Where to render the table of contents.
            tocSelector: '.mobile-tocbot',
            // Where to grab the headings to build the table of contents.
            contentSelector: '.typo-selection',
            // Which headings to grab inside of the contentSelector element.
            headingSelector: 'h1, h2, h3',
            hasInnerContainers: true,
        });
    }
}
//回复按钮-----Thymeleaf模板引擎下 使用局部刷新后 jQuery事件绑定失效的问题
//所以这种绑定需要更换
// $('.reply-btn').click(function (e) {
//     //拿到data-*
//     const formSelector = e.target.dataset.formselector;
//     $('.reply-close-btn.' + formSelector).show()
//     console.log($('.reply-close-btn.' + formSelector))
//     $('.reply-form.' + formSelector).show()
// })
$(document).on('click','.reply-btn',function (e) {
    //拿到data-*
    const formSelector = e.target.dataset.formselector;
    $('.reply-close-btn.' + formSelector).show()
    // console.log($('.reply-close-btn.' + formSelector))
    $('.reply-form.' + formSelector).show()
})
//关闭回复按钮
$(document).on('click','.reply-close-btn',function (e) {
    //拿到data-*
    const formSelector = e.target.dataset.formselector;
    $('.reply-close-btn.' + formSelector).hide()
    $('.reply-form.' + formSelector).hide()
})


//提交评论
$(document).on('click','.comment-btn',function (e) {
    //拿到data-*
    const formSelector = e.target.dataset.formselector;
    validAndSubmitForm('.ui.form' + '.' + formSelector);
})

//提交评论
function validAndSubmitForm(selector) {
    $(selector).form({
        fields: {
            content: {
                identifier: 'content',
                rules: [
                    {
                        type: 'empty',
                        prompt: '评论：请输入评论'
                    }
                ]
            },
            nickname: {
                identifier: 'nickname',
                rules: [
                    {
                        type: 'empty',
                        prompt: '昵称：请输入昵称'
                    }
                ]
            },
            email: {
                identifier: 'email',
                rules: [
                    {
                        type: 'email',
                        prompt: '请输入正确的邮箱地址'
                    },
                    {
                        type: 'empty',
                        prompt: '邮箱：请输入邮箱'
                    }
                ]
            }
        },
        onSuccess: function (event, fields) {
            //这里为啥调用了两遍？？？
            console.log("aaaa")
            // [*] 表单验证通过后调用 onSuccess 函数
            // fields 中保存了所有的表单数据，例如 {name: "Alice", color: "rgb(255, 255, 255)"}
            event.preventDefault(); // [*] 如果需要使用 Ajax 提交时，防止表单自动提交
            console.log(fields);
            //因为load方法确定了数据返回后渲染的位置，所以这里不选择表单，而是选择渲染位置
            $('#comment-list').load("/comments",{
                "parentComment.id" : fields['parentComment.id'],
                "blog.id" : fields['blog.id'],
                "nickname": fields.nickname,
                "email"   : fields.email,
                "content" : fields.content
            },function (responseTxt, statusTxt, xhr) {
                clearContent();
            });
        }
    })
}


function clearContent() {
    $("[name='content']").val('').attr("placeholder", "说点什么吧... ...");
    $("[name='nickname']").val('');
    $("[name='email']").val('');
}
