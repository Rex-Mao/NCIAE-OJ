package cn.edu.nciae.contentcenter.utils;

import cn.edu.nciae.contentcenter.common.entity.Checkpoint;
import cn.edu.nciae.contentcenter.common.entity.Sample;
import cn.edu.nciae.contentcenter.common.vo.ProblemVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

    public static List<ProblemVO> fps2ProblemVO(Long uid, String filepath) {
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
        List<Sample> sampleList = getSampleList(item);
        List<Checkpoint> checkpointList = getCheckpointList(item);
        NodeList nodeList = item.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String name = node.getNodeName();
            String value = node.getTextContent();
            if ("title".equalsIgnoreCase(name)) {
                problemVO.setTitle(value);
            }
            if ("time_limit".equalsIgnoreCase(name)) {
                Element tmp = (Element)node;
                String unit = tmp.getAttribute("unit");
                if ("s".equalsIgnoreCase(unit)) {
                    problemVO.setTimeLimit(Double.parseDouble(String.valueOf(value.split(" ")[0])) * 1000);
                } else {
                    problemVO.setTimeLimit(Double.valueOf(String.valueOf(value.split(" ")[0])));
                }
            }
            if ("memory_limit".equalsIgnoreCase(name)) {
                Element tmp = (Element)node;
                String unit = tmp.getAttribute("unit");
                if ("kb".equalsIgnoreCase(unit)) {
                    problemVO.setMemoryLimit(Double.parseDouble(String.valueOf(value.split(" ")[0])) / 1024);
                } else {
                    problemVO.setMemoryLimit(Double.valueOf(String.valueOf(value.split(" ")[0])));
                }
            }
            if ("description".equalsIgnoreCase(name)) {
                problemVO.setDescription(value);
            }
            if ("input".equalsIgnoreCase(name)) {
                problemVO.setFInput(value);
            }
            if ("output".equalsIgnoreCase(name)) {
                problemVO.setFOutput(value);
            }
            if ("hint".equalsIgnoreCase(name)) {
                problemVO.setHint(value);
            }
            if ("source".equalsIgnoreCase(name)) {
                problemVO.setAuthor(value);
            }
//            if (name.equalsIgnoreCase("img")) {
//                problem.imageList.add(new freeproblemset.Image(e, p));
//            }
        }
        problemVO.setSubmitNum(0);
        problemVO.setSolvedNum(0);
        problemVO.setSamples(sampleList);
        problemVO.setCheckpoints(checkpointList);
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

    public static List<Sample> getSampleList(Node item) {
        List<Sample> sampleList = new ArrayList<>(5);
        NodeList sampleInputTags = ((Element)item).getElementsByTagName("sample_input");
        NodeList sampleOutputTags = ((Element)item).getElementsByTagName("sample_output");
        for (int i=0; i < sampleInputTags.getLength(); i++) {
            Element elementIn = (Element) sampleInputTags.item(i);
            Element elementOut = (Element) sampleOutputTags.item(i);
            Sample sample = Sample.builder().input(elementIn.getTextContent())
                    .output(elementOut.getTextContent())
                    .build();
            sampleList.add(sample);
        }
        return sampleList;
    }

    public static List<Checkpoint> getCheckpointList(Node item) {
        List<Checkpoint> checkpointList = new ArrayList<>(20);
        NodeList checkpointInputTags = ((Element)item).getElementsByTagName("test_input");
        NodeList checkpointOutputTags = ((Element)item).getElementsByTagName("test_output");
        for (int i=0; i < checkpointInputTags.getLength(); i++) {
            Element elementIn = (Element) checkpointInputTags.item(i);
            Element elementOut = (Element) checkpointOutputTags.item(i);
            Checkpoint checkpoint = Checkpoint.builder().input(elementIn.getTextContent())
                    .output(elementOut.getTextContent())
                    .build();
            checkpointList.add(checkpoint);
        }
        return checkpointList;
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
