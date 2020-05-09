package com.sample;

import program.GetUserApproval;
import program.run.Go;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(
    name = "resultservlet",
    urlPatterns = "/result"
)
public class ResultServlet extends HttpServlet {

    private static String name;
    private static String desc;
    private static String sentence;
    private static boolean allowDuplicates;

    private static String playlistID = null;
    private static String code;

    private static boolean firstAttempt = true;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sentence = req.getParameter("sentence").replace("\n", " ");
        allowDuplicates = req.getParameter("allowDups") != null;
        ArrayList<String> invalidWords;

        if (firstAttempt) {
            playlistID = null;
            name = req.getParameter("name");
            desc = req.getParameter("desc");
            String uri = GetUserApproval.mkURI();
            req.setAttribute("uri", uri);
            RequestDispatcher view = req.getRequestDispatcher("clickUri.jsp");
            view.forward(req,resp);
            firstAttempt = false;
        } else {
            invalidWords = Go.nextAttempt(sentence, allowDuplicates);

            if (invalidWords.size() == 0) {
                playlistID = Go.getID();
                req.setAttribute("playlistID", playlistID);
                RequestDispatcher view = req.getRequestDispatcher("result.jsp");
                view.forward(req, resp);
                reset();
            } else {
                req.setAttribute("originalSentence", sentence);
                req.setAttribute("invalidWords", invalidWords);
                RequestDispatcher view = req.getRequestDispatcher("invalidWord.jsp");
                view.forward(req, resp);
                sentence = null;
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (sentence == null) { // only happens if reset() has been called or an invalid word has been found
            RequestDispatcher view = req.getRequestDispatcher("gohome.jsp");
            view.forward(req, resp);
            return;
        }

        String fullQuery = "";
        fullQuery += req.getQueryString();

        if (fullQuery.contains("code")) {
            code = fullQuery.substring(fullQuery.indexOf("=") + 1);
            ArrayList<String> invalidWords = Go.firstAttempt(code, sentence, name, desc, allowDuplicates);

            if (invalidWords.size() == 0) {
                playlistID = Go.getID();
                req.setAttribute("playlistID", playlistID);
                RequestDispatcher view = req.getRequestDispatcher("result.jsp");
                view.forward(req, resp);
                reset();
            } else {
                req.setAttribute("originalSentence", sentence);
                req.setAttribute("invalidWords", invalidWords);
                RequestDispatcher view = req.getRequestDispatcher("invalidWord.jsp");
                view.forward(req, resp);
                sentence = null;
            }
            return;
        }

        RequestDispatcher view = req.getRequestDispatcher("gohome.jsp");
        view.forward(req, resp);
    }

    private static void reset() {
        name = null;
        desc = null;
        sentence = null;
        playlistID = null;
        code = null;
        firstAttempt = true;
        allowDuplicates = false;
    }
}