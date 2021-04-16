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

$('.wechat-pop-up-button')
  .popup({
    popup: $('.wechat-pop-up.popup'),
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
  colorDark : "#000000",
  colorLight : "#ffffff",
  correctLevel : QRCode.CorrectLevel.H
});

function adjustBlogColumn() {
  if ($(window).width() > 768) {
    $('.ui.sticky').sticky('refresh');
    $('#blog').removeClass('fourteen wide column').addClass('eleven wide column')
    $('#blog-content').addClass('attached bottom').removeAttr('style')
  } else {
    $('.ui.sticky.mobile').sticky('refresh');
    $('#blog').removeClass('eleven wide column').addClass('fourteen wide column')
    $('#blog-content').removeClass('attached bottom').css("margin", "0")

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
