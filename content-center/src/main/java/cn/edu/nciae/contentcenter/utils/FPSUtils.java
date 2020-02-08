package cn.edu.nciae.contentcenter.utils;

import cn.edu.nciae.contentcenter.common.vo.ProblemVO;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/7 5:52 PM
 */
public class FPSUtils {

    private static NodeList itemList;

    public static List<ProblemVO> FPS2ProblemVO(Long uid, String filepath) {
        Document doc;
        doc = parseXML(filepath);
        List<ProblemVO> problems = new ArrayList<ProblemVO>();
        itemList = doc.getElementsByTagName("item");
        for (int i = 0; i < itemList.getLength(); i++) {
            ProblemVO problemVO = itemToProblemVO(itemList.item(i));
            problemVO.setAddUid(uid);
            problems.add(problemVO);
        }
        return problems;
    }

    private static ProblemVO itemToProblemVO(Node item) {
        ProblemVO problemVO = new ProblemVO();
        NodeList nodeList = item.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String name = node.getNodeName();
            String value = node.getTextContent();
            if (name.equalsIgnoreCase("title")) {
                problemVO.setTitle(value);
            }
            if (name.equalsIgnoreCase("time_limit")) {
                problemVO.setTimeLimit(Double.valueOf(String.valueOf(value.split(" ")[0])));
            }
            if (name.equalsIgnoreCase("memory_limit")) {
                problemVO.setMemoryLimit(Double.valueOf(String.valueOf(value.split(" ")[0])));
            }
            if (name.equalsIgnoreCase("description")) {
                problemVO.setDescription(value);
            }
            if (name.equalsIgnoreCase("input")) {
                problemVO.setFInput(value);
            }
            if (name.equalsIgnoreCase("output")) {
                problemVO.setFOutput(value);
            }
//            if (name.equalsIgnoreCase("sample_input")) {
//                problem.setSInput(value);
//            }
//            if (name.equalsIgnoreCase("sample_output")) {
//                problem.setSOutput(value);
//            }
//            if (name.equalsIgnoreCase("hint")) {
//                problem.hint = p.setImages(value);
//            }
            if (name.equalsIgnoreCase("source")) {
                problemVO.setAuthor(value);
            }
//            if (name.equalsIgnoreCase("img")) {
//                problem.imageList.add(new freeproblemset.Image(e, p));
//            }
        }
        problemVO.setSubmitNum(0);
        problemVO.setSolvedNum(0);
        return problemVO;
    }

    private static Document parseXML(String filepath) {
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(filepath);
            doc.normalize();

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return doc;
    }

//    public static String setImages(String html) {
//        // TODO Auto-generated method stub
//        Vector<Image> imageList = new Vector<Image>();
//        Iterator<Image> i = imageList.iterator();
//        while (i.hasNext()) {
//            Image img = i.next();
//            html = html.replaceAll(img.oldURL, img.URL);
//        }
//        return html;
//    }
}

//class Image {
//    private static int counter = 0;
//    public int num = counter++;
//    public Problem problem;
//    public String oldURL = "";
//    public String URL = "";
//
//    public Image(Node node, Problem problem) {
//        this.problem = problem;
//        NodeList nodeList = node.getChildNodes();
//        oldURL = nodeList.item(0).getTextContent();
//        Random random = new Random();
//        URL = "images/pic" + random.nextInt() + "_" + num;
//        try {
//
//            byte[] decodeBuffer = new sun.misc.BASE64Decoder()
//                    .decodeBuffer(nodeList.item(1).getTextContent());
//            FileOutputStream fo = new FileOutputStream("cache/" + URL);
//            fo.write(decodeBuffer);
//            fo.close();
//        } catch (DOMException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//    }
//}
