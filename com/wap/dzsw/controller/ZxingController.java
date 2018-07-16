package com.wap.dzsw.controller;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 
		
		* 描述:
		*	用于生成二维码和条形码
		* @author 许宝众
		* @version 1.0
		* @since 2017年7月12日 上午9:45:55
 */
@Controller
@RequestMapping("/zxing")
public class ZxingController {
	/**二维码默认宽度，单位（像素）**/
	public static final int QRCODE_DEFAULT_WIDTH=200;
	/**二维码默认高度，单位（像素）**/
	public static final int QRCODE_DEFAULT_HEIGHT=200;
	/**条形码默认宽度，单位（像素）**/
	public static final int BARCODE_DEFAULT_WIDTH=290;
	/**条形码默认宽度，单位（像素）**/
	public static final int BARCODE_DEFAULT_HEIGHT=100;
	
	/**
	 * 生成指定宽、高、内容的二维码
		* 描述:
		* @param width
		* @param height
		* @param content
		* @author 许宝众 2017年7月12日 上午9:50:33
	 * @throws Exception 
	 */
	@RequestMapping("qrcode")
	public void qrcode(Integer width,Integer height,String content,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Assert.isTrue(StringUtils.isNotBlank(content),"二维码内容不能为空");
		if(width==null||width<1){
			width=QRCODE_DEFAULT_WIDTH;
		}
		if(height==null||height<1){
			height=QRCODE_DEFAULT_HEIGHT;
		}
        final String format = "png";// 图像类型  
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        BitMatrix bitMatrix;
		// 生成矩阵  
		bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);
		ServletOutputStream os = response.getOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, format, os);
		closeOutStream(os);
	}
	@RequestMapping("barcode")
	public void barcode(Integer width,Integer height,String content,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Assert.isTrue(StringUtils.isNotBlank(content),"条形码内容不能为空");
		if(width==null||width<1){
			width=BARCODE_DEFAULT_WIDTH;
		}
		if(height==null||height<1){
			height=BARCODE_DEFAULT_HEIGHT;
		}
		final String format = "png";// 图像类型  
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,  
                BarcodeFormat.CODE_128, width, height, hints);// 生成矩阵  
        ServletOutputStream os = response.getOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, format, os);  
        closeOutStream(os);
	}
	public void closeOutStream(OutputStream os){
		if(os!=null){
			try{
				os.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//转发到wx_interface
	@RequestMapping(value={"/{path}"})
	public void updateOrderMseeageKlaDing(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String servletPath = request.getServletPath().substring("/zxing/".length());
		String forwardPath="/wx_interface/"+servletPath;
		
		request.getRequestDispatcher(forwardPath).forward(request, response);
	}
}
