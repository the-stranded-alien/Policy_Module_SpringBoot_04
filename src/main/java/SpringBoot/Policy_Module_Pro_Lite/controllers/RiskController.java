package SpringBoot.Policy_Module_Pro_Lite.controllers;

import SpringBoot.Policy_Module_Pro_Lite.models.Policy;
import SpringBoot.Policy_Module_Pro_Lite.models.Risk;
import SpringBoot.Policy_Module_Pro_Lite.services.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/risk")
public class RiskController {

    @Autowired
    private RiskService riskService;

    // Display List of All Risks Created By The Current Logged In User
    @GetMapping
    public String viewRiskHomePage(Model model) {
        return findPaginated(1, "title", "asc", model);
    }

    @GetMapping("/viewRisk/{id}")
    public String viewRisk(@PathVariable(value = "id") Long id, Model model) {
        Risk risk = this.riskService.getRiskById(id);
        model.addAttribute("risk", risk);
        return "risk/viewRisk";

    }

    @PostMapping("/updateRisk")
    public String updateRisk(@ModelAttribute("risk") Risk risk) {
        Risk updatedRisk = this.riskService.updateRisk(risk);
        return "redirect:/risk";
    }

    // For Adding A New Risk
    @GetMapping("/showNewRiskForm")
    public String showNewRiskForm(Model model) {
        Risk risk = new Risk();
        model.addAttribute("risk", risk);
        return "risk/newRisk";
    }

    // For Saving the Added/Updated Risk
    @PostMapping("/saveRisk")
    public String saveRisk(@ModelAttribute("risk") Risk risk) {
        // Save Risk to the Database
        riskService.saveRisk(risk);
        return "redirect:/risk";
    }

    // For Updating a Particular Risk
    @GetMapping("/showUpdateRiskForm/{id}")
    public String showUpdateRiskForm(@PathVariable(value="id") Long id, Model model) {
        // Get the Risk from the Service Using the "id"
        Risk risk = riskService.getRiskById(id);
        // Set Risk as a Model Attribute to Pre-populate the Form !
        model.addAttribute("risk", risk);
        return "risk/updateRisk";
    }

    // Using Iterators : You Can't Remove Elements While Iterating Through Them Using For-Each Sort Of Loops. So, Use An Iterator Instead.
    // Using Iterators With Transactional Is Also Not Working !!
//    @GetMapping("/deleteRisk/{id}")
//    @Transactional
//    public String deleteRisk(@PathVariable(value="id") Long id) {
//        Risk risk = riskService.getRiskById(id);
//        Set<Policy> policies = risk.getPoliciesIncludedIn();
//        for(Iterator<Policy> policyIterator = policies.iterator(); policyIterator.hasNext();) {
//            Policy curPolicy = policyIterator.next();
//            if(curPolicy.getRisksInvolved().contains(risk)) {
//                curPolicy.removeRisk(risk);
//            }
//        }
//        this.riskService.deleteRiskById(id);
//        return "redirect:/risk";
//    }

    // Working But Utility Function For Bidirectional Synced Removal Is Not Used
    @GetMapping("/deleteRisk/{id}")
    public String deleteRisk(@PathVariable(value="id") Long id) {
        Risk risk = riskService.getRiskById(id);
        for(Policy policy : risk.getPoliciesIncludedIn()) {
            // Can't Use Bidirectional Sync Utility Function Directly Here
            // The Bidirectional Utility Function For Remove Won't Work
            // Concurrent Modification Exception Would Arise !
            // Nothing To Do With @Transactional !? Maybe !? Not Sure !!
            policy.getRisksInvolved().remove(risk);
        }
        risk.getPoliciesIncludedIn().clear();
        this.riskService.deleteRiskById(id);
        return "redirect:/risk";
    }


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value="pageNo") Integer pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        Integer pageSize = 5;
        Page<Risk> page = riskService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Risk> listRisks = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listRisks", listRisks);

        return "risk/homeRisk";
    }
}
