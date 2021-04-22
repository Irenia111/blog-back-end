var container;

$(function () {
  // You can custom Emoji's graphics files url path
  editormd.emoji     = {
    path  : "http://www.emoji-cheat-sheet.com/graphics/emojis/",
    ext   : ".png"
  };
  container = editormd("content-editormd", {
    width: "100%",
    height: 500,
    syncScrolling : "single",
    // 由于存在模板，所以这里的path需要修改 path: "../static/lib/editormd/lib/",
    path: "/lib/editormd/lib/",
    saveHTMLToTextarea: true,
    emoji: true,
    taskList: true,
    tocm: true,       // Using [TOCM]
    toolbarIcons : () => ["undo","redo",
      "|","bold","del","italic","quote","ucwords","uppercase","lowercase",
      "|","list-ul","list-ol","hr",
      "|","link","reference-link","image","code","preformatted-text",
      "|","table","datetime","emoji","html-entities","pagebreak",
      "goto-line","watch","preview","fullscreen","clear","search",
      "|","help","info"]
  });
});
