<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to the system</title>
</head>
<body>
<script type="text/javascript">
	function validateForm(login)
	{
		var reasonDisplayed="";
		var flag=true;
		reasonDisplayed+=validateUsername(document.getElementById(username));
		reasonDisplayed+=validatePassword(document.getElementById(password));
	 
		if(reasonDisplayed!="")
		{
			alert(reasonDisplayed);
			flag=false;
		}
		return flag;
	}
	
	function validateUsername(username)
	{
		var errorMsg="";
		
		//check for empty
		if(username.value.length===0)
		{
			username.style.background="Yellow";
			errorMsg="Username is empty \n";
			return errorMsg;
		}
		//length check
		else
		if(username.value.length>15 || username.value.length<7)
		{
			username.style.background="Yellow";
			errorMsg="Username is either too long or too short \n";
			return errorMsg;
		}
		//illegal character check
		else
		if(/\W/g.test(username.value))
		{
			errorMsg="Username contains illegal characters, please re-enter";
	        username.style.background="yellow";
	        return errorMsg;
		}
		return errorMsg;
	}
	
	function validatePassword(pwd)
    {
        var errorMsg="";
        
        if(pwd.value.length === 0)
        {
            errorMsg="Empty password";
            pwd.style.background="yellow";
        }
        else if(pwd.value.length<7 || pwd.value.length>13)
        {
            errorMsg="Password should be a minimum of 7 or maximum of 13 characters";
            fld.style.background="yellow";
        }
        else if(!/\W/g.test(pwd.value))
        {
            errorMsg="Password should contain atleast 1 number, 1 special character such as *,%,!,$, please re-enter";
            pwd.style.background="yellow";
        }
        return errorMsg;
    }
</script>

	<form name="login" action="homepage.jsp" onsubmit="return validateForm(login)" method=POST>
	<p>
		<label for="username">Username:</label>
		<input type="text" id="username" name="username"/><br/>
	</p>
	<p>
		<label for="password">Password:</label>
		<input type="password" id="password" name="password"/><br/>
	</p>
	<p>
		<label for="login"></label>
		<input type="submit" value="Login"/><br/>
	</p>
	<a href="registeruser.jsp">New user? Click here !!!</a><br/>
	</form>
</body>
</html>