package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - done - get the Job with the given ID and pass it into the view
        //get the job by the id then add the job to the model
        Job singleJob = jobData.findById(id);
        model.addAttribute("job", singleJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            model.addAttribute("jobForm", jobForm);
            model.addAttribute("errors", errors);
            return "new-job";
        }
        String theName = jobForm.getName();

        int theEmployerId = jobForm.getEmployerId();

        int theLocationId = jobForm.getLocationId();

        int thePositionId = jobForm.getPositionTypeId();

        int theCompetencyId = jobForm.getCoreCompetencyId();

        Employer theEmployer = jobData.getEmployers().findById(theEmployerId);
        Location theLocation = jobData.getLocations().findById(theLocationId);
        PositionType thePosition = jobData.getPositionTypes().findById(thePositionId);
        CoreCompetency theCompetency = jobData.getCoreCompetencies().findById(theCompetencyId);


        Job newJob = new Job(theName, theEmployer, theLocation, thePosition, theCompetency);
        jobData.add(newJob);

        //take the user to their new job.

        return "index";

    }
}
