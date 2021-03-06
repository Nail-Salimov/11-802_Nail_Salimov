package Filters;

import BDlogical.UsersRepository;
import BDlogical.UsersRepositoryImpl;
import Model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;


//@WebFilter("/profile")
public class ProfileFilter implements Filter {
    private UsersRepository repository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        repository = UsersRepositoryImpl.getRepository();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;



        Cookie[] cookies = request.getCookies();
        String cookieMail = null;
        String cookiePassword = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals("mail")) {
                    cookieMail = cookie.getValue();
                } else if (name.equals("password")) {
                    cookiePassword = cookie.getValue();
                }
            }
        }

        Optional<User> userOptional = repository.findUser(cookieMail, cookiePassword);
        if (userOptional.isPresent()) {
            HttpSession httpSession = request.getSession();
            User user = userOptional.get();
            httpSession.setAttribute("mail", user.getMail());
            httpSession.setAttribute("id", user.getId());
        }

        HttpSession session = request.getSession(false);
        System.out.println(session.getAttribute("mail"));
        if (session == null || session.getAttribute("mail") == null || session.getAttribute("id") == null) {
            //request.getRequestDispatcher("/login").forward(request, response);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
