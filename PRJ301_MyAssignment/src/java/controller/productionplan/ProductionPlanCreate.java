package controller.productionplan;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import dal.DepartmentDBContext;
import dal.PlanDBContext;
import dal.ProductDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import model.Department;
import model.Plan;
import model.PlanCampaign;
import model.Product;

/**
 *
 * @author Minh Duc
 */
public class ProductionPlanCreate extends HttpServlet {
   
    /** 
     * Processes reqs for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param req servlet req
     * @param resp servlet resp
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDept = new DepartmentDBContext();
        req.setAttribute("products", dbProduct.list());
        req.setAttribute("depts", dbDept.get("workshop"));
        req.getRequestDispatcher("../view/productionplan/create.jsp").forward(req, resp);
           
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param req servlet req
     * @param resp servlet resp
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        processRequest(req, resp);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param req servlet req
     * @param resp servlet resp
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        String[] pids = req.getParameterValues("pid");
        
        Plan plan = new Plan();
        plan.setStartd(Date.valueOf(req.getParameter("from")));
        plan.setEndd(Date.valueOf(req.getParameter("to")));
        
        Department d = new Department();
        d.setDid(Integer.parseInt(req.getParameter("did")));
        
        plan.setDepartment(d);
        plan.setCampaigns(new ArrayList<>());
        
        for (String pid : pids) {
            Product p = new Product();
            p.setpID(Integer.parseInt(pid));
            
            PlanCampaign c = new PlanCampaign();
            c.setProduct(p);
            String raw_quantity = req.getParameter("quantity"+pid);
            String raw_effort = req.getParameter("effort"+pid);
            c.setQuantity(raw_quantity != null && raw_quantity.length()>0?Integer.parseInt(raw_quantity):0);
            c.setEstimatedeffort(raw_effort != null && raw_effort.length()>0?Float.parseFloat(raw_effort):0);
            c.setPlan(plan);
            if(c.getQuantity()!=0 && c.getEstimatedeffort()!=0)
                plan.getCampaigns().add(c);
        }
        
        if(plan.getCampaigns().size()>0)
        {
            PlanDBContext db = new PlanDBContext();
            db.insert(plan);
            resp.getWriter().println("created a new plan!");
        }
        else
        {
            resp.getWriter().println("your plan did not have any campains");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
