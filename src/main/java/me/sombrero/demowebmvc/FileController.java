package me.sombrero.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

    /**
     * 파일 업로드 페이지.
     */
    @GetMapping("/file")
    public String fileUploadForm(Model model) {
        // 파일 업로드 로직에서 다시 돌아오면 RedirectAttributes로 인해 model에 message가 담겨있다.
        return "files/index";
    }

    /**
     * 파일 업로드 로직.
     */
    @PostMapping("/file")
    public String fileUpload(@RequestParam MultipartFile file, // form에서 사용한 이름과 동일하게 해준다.
                            RedirectAttributes attributes) {
        // save하는 로직이 들어가는 곳. (지금은 생략.)

        System.out.println("##### file name: " + file.getName()); // form에서 사용한 이름.
        System.out.println("##### file original name: " + file.getOriginalFilename()); // 실제 업로드한 파일 이름.

        String message = file.getOriginalFilename() + " is uploaded."; // 업로드 되었다는 메세지.
        attributes.addFlashAttribute("message", message); // 메세지를 RedirectAttributes로 플래시 애트리뷰트에 추가한다.
        return "redirect:/file"; // GET요청의 /file 페이지로 리다이렉트.
    }

}
