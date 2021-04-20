$('.ui.dropdown').dropdown();

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

$('#clear-btn')
    .on('click', function() {
      $('.ui.type.dropdown')
          .dropdown('clear')
      ;
    })
;

/* 局部刷新分页内容
 *
 * 获得当前页数，然后将页数和搜索信息一起post
 * 在搜索时提交全部信息，服务器根据post请求返回部分数据
**/
function page(obj) {
  //给分页按钮重新赋值
  $("[name='page']").val($(obj).data("page"));
  //提交form表单，修改模板中的fragment
  loaddata();
}

$("#search-btn").click(function () {
  $("[name='page']").val(0);
  loaddata();
});

function loaddata() {
  //根据表单内容，发送ajax请求
  $("#table-container").load(/*[[@{/admin/blogs/search}]]*/"/admin/blogs/search",{
    title : $("[name='title']").val(),
    typeId : $("[name='typeId']").val(),
    recommend : $("[name='recommend']").prop('checked'),
    page : $("[name='page']").val()
  });
}

