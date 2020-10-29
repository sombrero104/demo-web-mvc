package me.sombrero.demowebmvc;

import org.apache.catalina.loader.ResourceEntry;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private ResourceLoader resourceLoader;

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
        // save하는 로직이 들어가는 곳. 이곳에서 파일을 스토리지에 저장하는 로직을 작성하면 된다. (지금은 생략.)

        System.out.println("##### file name: " + file.getName()); // form에서 사용한 이름.
        System.out.println("##### file original name: " + file.getOriginalFilename()); // 실제 업로드한 파일 이름.

        String message = file.getOriginalFilename() + " is uploaded."; // 업로드 되었다는 메세지.
        attributes.addFlashAttribute("message", message); // 메세지를 RedirectAttributes로 플래시 애트리뷰트에 추가한다.
        return "redirect:/file"; // GET요청의 /file 페이지로 리다이렉트.
    }

    /**
     * 파일 다운로드
     */
    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> fileDownload(@PathVariable String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filename); // 다운로드 받을 파일.
        File file = resource.getFile();

        Tika tika = new Tika(); // 빈으로 등록해서 사용해도 됨.
        String mediaType = tika.detect(file); // 티카 라이브러리로 미디어 타입 알아내기.

        return ResponseEntity.ok() // 응답코드 200 정상.
                .header(HttpHeaders.CONTENT_DISPOSITION, // 다운로드 받을 때 파일 이름.
                        "attachement; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, mediaType) // 미디어타입. (image/png)
                .header(HttpHeaders.CONTENT_LENGTH, file.length() + "") // 파일 크기
                .body(resource); // body에 다운로드할 resource인 파일을 담아서 줌.
    }

}
