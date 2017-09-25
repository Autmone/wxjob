package com.autmone.softmarket.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageUtil {

	/**
	 * 合成图片
	 * @param content	二维码内容
	 * @param path		背景图片地址
	 * @param startX	二维码在背景图片的X轴位置
	 * @param startY	二维码在背景图片的Y轴位置
	 * @param codeWidth	二维码宽度
	 * @param codeHeight 二维码高度
	 * @return			合成的图片
	 */
	public static BufferedImage compositeImage(String content, String path, int startX, int startY, int codeWidth, int codeHeight) {
		try {
			BufferedImage headImage = createImage(content, null, codeWidth, codeHeight, true);
			
			String backBIS64 = ""; 
			Image backImage = null;
			
			FileInputStream fileInputStream = new FileInputStream(path);
			backBIS64 = ImageUtil.GetImageStr(fileInputStream);
				// 读取背景图片
			InputStream in = new ByteArrayInputStream(GenerateImage(backBIS64));
			backImage = ImageIO.read(in);
			int alphaType = BufferedImage.TYPE_INT_RGB;
			if (ImageUtil.hasAlpha(backImage)) {
				alphaType = BufferedImage.TYPE_INT_ARGB;
			}
			BufferedImage back = new BufferedImage(backImage.getWidth(null), backImage.getHeight(null), alphaType);

			// 画图
			Graphics2D g = back.createGraphics();
			g.drawImage(backImage, 0, 0, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
			g.drawImage(headImage, startX, backImage.getHeight(null) - startY, headImage.getWidth(null), headImage.getHeight(null), null);

			g.dispose();

			return back;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 二维码图片的生成
	 * @param content			链接
	 * @param imgPath			图片存储地址
	 * @param qrcode_width		二维码宽
	 * @param qrcode_height		二维码高
	 * @param needCompress		是否需要压缩
	 * @return
	 * @throws Exception
	 */
    public static BufferedImage createImage(String content, String imgPath, int qrcode_width, int qrcode_height ,boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, qrcode_width, qrcode_height, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        return image;
    }
    
    /**
	 * @param im
	 *            原始图像
	 * @param im
	 *            缩放后的宽
	 * @param im
	 *            缩放后的高
	 * @param resizeTimes
	 *            倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @return 返回处理后的图像
	 */
	public static BufferedImage zoomImage(BufferedImage im, double resizeTimes) {

		BufferedImage result = null;

		try {
			String s = im.getWidth()*resizeTimes + "" ;
			String ss = im.getHeight()*resizeTimes +"" ;
			int toWidth = Integer.parseInt(s.split("\\.")[0]) ;
			int toHeight = Integer.parseInt(ss.split("\\.")[0]) ;
			
			/* 新生成结果图片 */
			result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);

			result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
					null);

		} catch (Exception e) {
			System.out.println("创建缩略图发生异常" + e.getMessage());
		}

		return result;

	}
	
	/**
	 * 图片转base64
	 * 
	 * @param imgFilePath
	 * @return
	 */
	public static String GetImageStr(FileInputStream fileInputStream) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;

		// 读取图片字节数组
		try {
			InputStream in = fileInputStream;
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
	
	/**
	 * base64转图片
	 * 
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 */
	public static byte[] GenerateImage(String base64) {// 对字节数组字符串进行Base64解码并生成图片
		if (base64 == null) // 图像数据为空
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(base64);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			return bytes;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 是否开启alpha通道
	 * 
	 * @param image
	 * @return
	 */
	public static boolean hasAlpha(Image image) {
		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage) image;
			return bimage.getColorModel().hasAlpha();
		}
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}
}
