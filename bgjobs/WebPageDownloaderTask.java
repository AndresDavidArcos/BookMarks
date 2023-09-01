package bgjobs;

import dao.BookmarkDao;
import entities.WebLink;
import utils.HttpConnect;
import utils.IOUtil;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WebPageDownloaderTask implements Runnable{
    private static BookmarkDao dao = new BookmarkDao();
    private static final long TIME_FRAME = 3000000000L; //3 segundos
    private boolean downloadAll = false;
    ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);

    public WebPageDownloaderTask(boolean downloadAll){
        this.downloadAll = downloadAll;
    }

    private List<WebLink> getWebLinks(){
        List<WebLink> webLinks = null;
        if (downloadAll){
            webLinks = dao.getAllWebLinks();
            downloadAll = false;
        }else {
            webLinks = dao.getWebLinks(WebLink.DownloadStatus.NOT_ATTEMPTED);
        }
        return webLinks;
    }

    private List<Downloader<WebLink>> getTasks(List<WebLink> webLinks){
        List<Downloader<WebLink>> tasks = new ArrayList<>();

        for(WebLink webLink : webLinks){
            tasks.add((new Downloader<WebLink>(webLink)));
        }

        return tasks;
    }


    private void download(List<WebLink> webLinks){
        List<Downloader<WebLink>> tasks = getTasks(webLinks);
        List<Future<WebLink>> futures = new ArrayList<>();

        try {
            futures = downloadExecutor.invokeAll(tasks, TIME_FRAME, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<WebLink> future : futures) {
            try {
                if (!future.isCancelled()) {
                    WebLink webLink = future.get();
                    String webPage = webLink.getHtmlPage();
                    if (webPage != null){
                        IOUtil.write(webPage, webLink.getId());
                        webLink.setDownloadStatus(WebLink.DownloadStatus.SUCCESS);
                        System.out.println("DOWNLOAD SUCCESS: "+webLink.getUrl());
                    }else{
                        System.out.println("WEBPAGE NOT DOWNLOADER "+webLink.getUrl());;
                    }
                } else {
                    System.out.println("\n\nTask is cancelled --> " + Thread.currentThread());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
    private static class Downloader<T extends WebLink> implements Callable<T> {
        private T weblink;
        public Downloader(T weblink) {
            this.weblink = weblink;
        }
        public T call() {
            try {
                if (!weblink.getUrl().endsWith(".pdf")){
                    weblink.setDownloadStatus(WebLink.DownloadStatus.FAILED);
                    String htmlPage = HttpConnect.download(weblink.getUrl());
                    weblink.setHtmlPage(htmlPage);
                }else {
                    weblink.setDownloadStatus(WebLink.DownloadStatus.NOT_ELIGIBLE);


                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return weblink;
        }
    }

    public void run(){
        while (!Thread.currentThread().isInterrupted()){
            List<WebLink> webLinks = getWebLinks();

            if (webLinks.size() > 0){
                download(webLinks);
            } else{
                System.out.println("No new web links to download");
            }

            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        downloadExecutor.shutdown();
    }

}
