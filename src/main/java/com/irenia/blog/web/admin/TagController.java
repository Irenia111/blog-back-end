package com.irenia.blog.web.admin;

import com.irenia.blog.NotFoundException;
import com.irenia.blog.prototype.Tag;
import com.irenia.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin")
public class TagController {
    //service注入
    @Autowired
    private TagService tagService;

    //分页列表展示
    @GetMapping("/tags")
    public String types(@PageableDefault(size = 8, sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/admin-tag";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/admin-tag-edit";
    }

    @PostMapping("/tags")
    public String post(Tag tag,
                       BindingResult result,
                       RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/admin-tag-edit";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("error", "新增失败");
        } else {
            attributes.addFlashAttribute("success", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(Tag tag,
                           BindingResult result,
                           @PathVariable Long id,
                           RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/admin-tag-edit";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("error", "更新失败");
        } else {
            attributes.addFlashAttribute("success", "更新成功");
        }
        return "redirect:/admin/tags";
    }


    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id,
                            Model model) {
        model.addAttribute("tag",
                tagService.getTag(id).orElseThrow(() -> new NotFoundException("tag can not be found")));
        return "admin/admin-tag-edit";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("success", "删除成功");
        return "redirect:/admin/tags";
    }
}