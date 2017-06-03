package com.mnayef.library.utils;

import com.mnayef.library.model.Link;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

/**
 * Created by Mohamed Hamdan on 2017-Jun-03.
 * mohamed.nayef95@gmail.com
 */
public class ParseUtils {

    /**
     * This method for get url info by @{@link Jsoup}.
     *
     * @param url  Url for get host.
     * @param html Html you want parse.
     * @return @{@link Link}
     */
    public static Link getLinkData(URL url, String html) {
        Link link = new Link();
        link.setUrl(url.getHost());

        Document document = Jsoup.parse(html);
        Elements elements = document.select("meta");
        link.setTitle(document.title());
        for (Element element : elements) {
            if (element.attr("property").equalsIgnoreCase("og:image") || element.attr("name").equalsIgnoreCase("image")) {
                link.setImage(element.attr("content"));
            } else if (element.attr("name").equalsIgnoreCase("description") || element.attr("property").equalsIgnoreCase("og:description")) {
                link.setDescription(element.attr("content"));
            } else if (element.attr("name").equalsIgnoreCase("title") || element.attr("property").equalsIgnoreCase("og:title")) {
                link.setTitle(element.attr("content"));
            }
        }
        link.setDescription(link.getDescription() != null ? link.getDescription() : "");
        link.setTitle(link.getTitle() != null ? link.getTitle() : "");
        link.setUrl(link.getUrl() != null ? link.getUrl().startsWith("www.") ? link.getUrl() : "www." + link.getUrl() : "");
        link.setImage(link.getImage() != null ? link.getImage() : getLinkImage(document, url));
        return link;
    }

    /**
     * This method for get first image from html page.
     *
     * @param document Html @{@link Document} for get img tags.
     * @param url      This url for get image url if image url is incomplete.
     * @return full image url or null.
     */
    private static String getLinkImage(Document document, URL url) {
        Elements elements = document.getElementsByTag("img");
        String src = elements.size() > 0 ? elements.get(0).attr("src") : null;
        return src != null && src.startsWith("http") ? src : src != null ? url.toString() + src : null;
    }

}
