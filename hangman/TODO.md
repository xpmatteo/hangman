
 - implement validation error in JsonResponse
 - validate user creation

 + refactor order of args in userBase.add

 + more users can log in and out


 - catch and handle 500 errors: return JSON



	// other urls should return 404
	// authenticated get to /users/1234
	// authenticated get to wrong /users/9999
	// unauth get to /prisoners
	// auth get to /prisoners
	// create user invalid or missing data
	// wrong userid

