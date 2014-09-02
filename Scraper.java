package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	public static ArrayList<String> siteUrl = new ArrayList<String>();
	static HashMap<String, String> siteAndHoroscope = new HashMap<String,String>();

	public static void crawl() {
		crawlCosmo();
		crawlDailyNews();
		crawlHuffPost();
	}

	public static String crawlCosmo() {
		String hor = "";
		try {
			Document doc = Jsoup.connect((String) siteUrl.get(0)).get();
			Elements elem = doc.select("div.content>p");
			for (Element e : elem) {
				hor = e.text();
				siteAndHoroscope.put("Cosmopolitan UK", hor);
			}
		} catch (IOException ex) {
			Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("---------------");
		return hor;
	}

	public static String crawlDailyNews() {
		String hor = "";
		try {
			Document doc = Jsoup.connect((String) siteUrl.get(1)).get();
			Elements elem = doc.select("#horoscope-body>p");
			for (Element e : elem) {
				hor = e.text();
				System.out.println("NY Daily News:" + hor);
				siteAndHoroscope.put("NY Daiy News", hor);
			}
		} catch (IOException ex) {
			Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("---------------");
		return hor;
	}
	
	public static String crawlHuffPost() {
		String hor = "";
		try {
			Document doc = Jsoup.connect((String) siteUrl.get(2)).get();
			Elements elem = doc.select("div.horRiverBody>p");
				Element e = elem.get(3);
				hor = e.text();
				System.out.println("Huffington Post:" + hor);
				siteAndHoroscope.put("Huffington Post", hor);
		} catch (IOException ex) {
			Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("---------------");
		return hor;
	}
	public static void domainNameMaker(String sign) {
		System.out.println("what is string"+sign);
		String cosmoUrl = "http://www.cosmopolitan.co.uk/horoscopes/" + sign;
		String nyDailyUrl = "http://www.nydailynews.com/life-style/horoscopes/daily?c="+ sign;
		String huffPostUrl = "http://www.huffingtonpost.com/horoscopes/astrology/"+sign;
		System.out.println("url hp" + huffPostUrl);
		siteUrl.add(cosmoUrl);
		siteUrl.add(nyDailyUrl);
		siteUrl.add(huffPostUrl);
		System.out.println("all the urls: " + cosmoUrl + " " + nyDailyUrl + " " + huffPostUrl);
	}

	public static void main(String[] args) {
		domainNameMaker("taurus");
		System.out.println("from scraper" + crawlCosmo());
	}

}
