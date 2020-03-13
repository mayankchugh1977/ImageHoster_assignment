package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageservice;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String saveComment(@PathVariable("imageId") Integer imageId, @RequestParam("comment") String comment, HttpSession session, Model model) {

        Image image = imageservice.getImage(imageId);
        User user = (User) session.getAttribute("loggeduser");
        Comment userComment = new Comment();
        userComment.setText(comment);
        userComment.setUser(user);
        userComment.setImage(image);
        userComment.setCreatedDate(new Date());
        commentService.saveComment(userComment);

        Image imageWithComments = imageservice.getImage(imageId);
        model.addAttribute("comments", userComment);
        model.addAttribute("image", imageWithComments);
        model.addAttribute("tags", imageWithComments.getTags());

        return "images/image";
    }
}
