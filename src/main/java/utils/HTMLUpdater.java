package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLUpdater {

    /**
     * Updates relative URLs in the HTML content to absolute URLs based on the provided base URL.
     *
     * @param htmlContent The HTML content as a string.
     * @param baseUrl     The base URL to resolve relative URLs against.
     * @return The updated HTML content with absolute URLs.
     */
    public static String updateLinks(String htmlContent, String baseUrl) {
        // Parse the HTML content into a Jsoup Document object using the base URL
        Document doc = Jsoup.parse(htmlContent, baseUrl);

        // Select all elements that have 'href' or 'src' attributes
        Elements links = doc.select("[href], [src]");

        // Iterate over each element and update its 'href' or 'src' attribute
        for (Element link : links) {
            if (link.hasAttr("href")) {
                // Update 'href' attribute to its absolute URL
                link.attr("href", link.absUrl("href"));
            } else if (link.hasAttr("src")) {
                // Update 'src' attribute to its absolute URL
                link.attr("src", link.absUrl("src"));
            }
        }

        // Return the HTML content as a string with updated links
        return doc.toString();
    }
}
