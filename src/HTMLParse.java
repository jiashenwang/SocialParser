import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HTMLParse {

	public static void main(String[] args) {
		
		ParseLinkedin("https://www.linkedin.com/pub/congming-chen/55/216/621");
		ParseTwitter("https://twitter.com/jason");
		//ParseFacebook("https://www.facebook.com/Google");

	}
	public static void ParseLinkedin(String url) {
		Document doc;
		try {
			System.out.println("-------------Start to Parse Linkedin -------------");
			doc = Jsoup.connect(url).get();
			System.out.println(doc.title());
			Element experienceContainer = doc.getElementById("background-experience");

			for (  Element item : experienceContainer.children()){
				Elements headers = item.getElementsByTag("header");
				if(headers.size()>=1){
					Element header = item.getElementsByTag("header").get(0);
					Elements titles = header.getElementsByTag("h4");
					if(titles.size()>=1){
						System.out.println("Title: "+titles.get(0).select("a").text());
					}
					Elements positions = header.getElementsByTag("h5");
					if(positions.size()>=1){
						if(positions.size() == 1){
							System.out.println("Where: "+positions.get(0).select("a").text() + " " +positions.get(0).select("span").text());
						}else{
							System.out.println("Where: "+positions.get(1).select("a").text() + " " +positions.get(1).select("span").text());
						}
					}
					Elements Periods = item.getElementsByTag("span");
					if(Periods.size()>=1){
						Elements times = Periods.get(0).getElementsByTag("time");
						if(times.size()>=1){
							if(times.size() == 1){
								System.out.println("From: "+times.get(0).text()+" to Present");
							}else{
								System.out.println("From: "+times.get(0).text()+" to "+times.get(1).text());
							}
						}
					}
					System.out.println("Description: "+item.select("p").text());
				}
				System.out.println();
			}
			System.out.println("-------------End Parsing Linkedin -------------");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public static void ParseTwitter(String url) {
		System.out.println("-------------Start to Parse Twitter -------------");
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			System.out.println(doc.title());
			Elements GridTimeline = doc.getElementsByClass("GridTimeline-items");
			for (  Element item : GridTimeline){
				if(item.children().size()>0 && item.children().get(0).className().equals("Grid")){
					Elements grids = item.getElementsByClass("Grid");
					for (Element grid : grids){
						Elements txtContents = grid.getElementsByTag("p");
						for(Element txtContent : txtContents){
							if(txtContent.className().equals("ProfileTweet-text js-tweet-text u-dir")){
								System.out.println("Posts: "+txtContent.text()+"\n");
							}
						}
					}
				}
			}
			//System.out.println(doc.getElementsByClass("GridTimeline-items"));
			System.out.println("-------------End Parsing Twitter -------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void ParseFacebook(String url) {
		System.out.println("-------------Start to Parse Facebook -------------");
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			System.out.println(doc.title());
			Elements lis = doc.getElementsByClass("_li");
			for(Element Li : lis){
				Elements scopes = Li.getElementsByAttributeStarting("itemscope");
				for(Element scope : scopes){
					System.out.println(scope.getElementById("contentArea"));
				}
			}
			//System.out.println(globalContainers.size());
//			System.out.println(clearfixes.size());
			
//			System.out.println(contents.size());
//			for(Element content : contents){
//				
//			}
//			System.out.println(contents.size());
//			for(Element content : contents){
//				Elements posts = content.getElementsByClass("_5pbx userContent");
//				for(Element post : posts){
//					System.out.println(post.text());
//				}
//			}
			
			System.out.println("-------------End Parsing Facebook -------------");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
