package kodlamaio.HRMS.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.JobSeekerCheckService;
import kodlamaio.HRMS.business.abstracts.JobSeekerService;
import kodlamaio.HRMS.business.validation.EmailValidatorService;
import kodlamaio.HRMS.business.validation.ValidationService;
import kodlamaio.HRMS.dataAccess.abstracts.JobSeekerDao;
import kodlamaio.HRMS.entities.concretes.JobSeeker;

@Service
public class JobSeekerManager implements JobSeekerService {

	JobSeekerDao jobSeekerDao;
	JobSeekerCheckService jobSeekerCheckService;
	EmailValidatorService emailValidatorService;
	ValidationService validationService;

	@Autowired
	public JobSeekerManager(JobSeekerDao jobSeekerDao, JobSeekerCheckService jobSeekerCheckService,
			EmailValidatorService emailValidatorService, ValidationService validationService) {
		super();
		this.jobSeekerDao = jobSeekerDao;

	}

	@Override
	public List<JobSeeker> getAll() {

		return this.jobSeekerDao.findAll();
	}

	@Override
	public void add(JobSeeker jobSeeker) {

		this.jobSeekerCheckService.CheckIfRealJobSeeker(jobSeeker);
		validate(jobSeeker);
		this.jobSeekerDao.save(jobSeeker);

	}

	private void validate(JobSeeker jobSeeker) {
		if (this.validationService.CheckIfExistJobSeeker(jobSeeker.getId()) == true) {
			System.out.println("Bu kişi zaten kayıtlıdır.");
			return;
		}
		if (this.emailValidatorService.validate(jobSeeker.getEmail()) == false) {
			System.out.println("Error : Email hatalıdır.");
			return;
		}
	}

	@Override
	public void delete(int id) {

		this.jobSeekerDao.deleteById(id);

	}

}
