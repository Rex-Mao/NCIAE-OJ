package cn.edu.nciae.contentcenter.controller;

import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.common.vo.WebsiteInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/9 12:54 PM
 */
@RestController
public class WebsiteInfoController {

    @GetMapping("/website")
    public MessageVO<WebsiteInfoVO> getWebsiteInfo() {
        return MessageVO.<WebsiteInfoVO>builder()
                .error(null)
                .data(WebsiteInfoVO.builder()
                .websiteBaseUrl("www.nciaeoj.com")
                .websiteName("NCIAE Online Judge")
                .websiteNameShortcut("NCIAE-OJ")
                .websiteFooter("Just Code It")
                .allowRegistry(true)
                .submissionListShowAll(true)
                .build())
                .build();
    }

//    @ResponseBody
//    @GetMapping("/favicon.ico")
//    public boolean getFavicon(HttpServletResponse httpServletResponse) throws IOException {
//        File file = new File("./mess/favicon.ico");
//        if (!file.exists()) {
//            return false;
//        }
//        FileInputStream fileInputStream = new FileInputStream(file);
//        byte[] data = new byte[(int)file.length()];
//        fileInputStream.read(data);
//        fileInputStream.close();
//
//        httpServletResponse.setContentType("image/x-icon");
//        OutputStream outputStream = httpServletResponse.getOutputStream();
//        outputStream.write(data);
//        outputStream.flush();
//        outputStream.close();
//        return true;
//    }

}
