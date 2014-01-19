========

boolean inBoolExpression = false, inArithExpression = false
boolean arithOperatorExists = false, boolOperatorExists = false
boolean arithOp1Exists = false, arithOp2Exists = false, leftIndexExists = false
boolean boolOp1Exists = false, boolOp2Exists = false

if(moveleft || moveright){
	if(inArithExpression){
		ERROR "You are in the middle of an arithmetic expression!"
	}
	if(inBoolExpression){
		ERROR "You are in the middle of a boolean expression!"
	}
}

if(interact){
	if(isincomputer){
		x = computervalue
		if(inBoolExpression){
			if(boolOp1Exists == false) // asign x to boolop1
				boolOp1 = x
				boolOp1Exists = true
			else if(boolOp1Exists == true){ // asign x to boolop2. boolexp end, evaluate
				if(operator == empty){
					ERROR "You need a boolean operator first!"
				}
				else{	
					boolOp2 = slot
					boolOp2Exists = true
					evaluateBooleanExpression()
				}
			}
		}
		else{
			if(!inArithExpression)
				ERROR "You can only use this in an expression!"
			else
				if(arithOp1Exists == false) // asign x to arithop1
					arithOp1 = x
					arithOp1Exists = true
				else if(arithOp1Exists == true) 
					if(arithOp exists) // asign x to arithop2. arithexp end, compute
						airthop2 = x
						arithOp2Exists = true
						computeArithmeticExpression()
					else
						ERROR "You need an arithmetic operator first!"
		}
	}
	else{
		ERROR " No computer found"
	}
}

if(slot){
	if(inBoolExpression){
		if(boolOp1Exists == false) // asign slot to boolop1
			boolOp1 = slot
			boolOp1Exists = true
		else if(boolOp1Exists == true) // asign slot to boolop2. boolexp end, evaluate
			if(operator == empty){
				ERROR "You need a boolean operator first!"
			}
			else{	
				boolOp2 = slot
				boolOp2Exists = true
				evaluateBooleanExpression()
			}
	}
	else{
		if(!inArithExpression)
			leftIndex = slot
		else
			if(arithOp1Exists == false) // asign slot to arithop1
				arithOp1 = slot
				arithOp1Exists = true
			else if(arithOp1Exists == true) 
				if(arithOp exists) // asign slot to arithop2. arithexp end, compute
					airthop2 = slot
					arithOp2Exists = true
					computeArithmeticExpression()
				else
					ERROR "You need an arithmetic operator first!"
	}
}
if(equals){
	if(inArithExpression)
		ERROR "You are already in an assignment statement!"
	else if(leftIndexExists)
		if(!arithOp1Exists)
			inArithExpression = true
	else{
		ERROR "You can only use equals in an assignment statement!"
	}
}
if(arithOperator){
	if(inArithExpression)
		if(arithOperatorExists)
			ERROR "You already have an arithemtic operator!"
		else{
			if(arithOp1Exists)
				arithOperator = arithOperator
				arithOpetaorExists = true
			else
				ERROR "You need arithmetic operand 1 first"
		}
	else
		ERROR "You need to be in an arithmetic expression first"
}

if(boolOperator){
	if(inBoolExpression)
		if(boolOpExists)
			ERROR "You already have a boolean operator!"
		else{
			if(boolOp1Exists)
				boolOp = boolOperator
			else
				ERROR "You need boolean operand 1 first"
		}
	else
		ERROR "You need to be in a boolean expression first (if/else statement)"
}

if(openIf){
	if(inBoolExpresion){
		ERROR "You are already in an if/else statement!"
	}
	else if(inArithExpression){
		ERROR "You are in the middle of an arithmetic expression!"
	}
	else{
		inBoolExpression = true
	}
}

if(closeIf){
	if(inIfBody)
		inIf
}

evaluateBooleanExpression(){
	switch(boolOp)
	case '=': boolOp1 == boolOp2; break;
	case '!=': boolOp1 != boolOp2; break;
	// etc

	boolOpResult = result from switch case

	resetBooleanExpression()
}

computeArithmeticExpression(){
	switch(arithOperator)
	case '+': Robot.inventory[leftIndex] = arithOp1 + arithOp2; break;
	resetArithmeticExpression();
}

resetArithmeticExpression(){
	arithOperatorExists = false
	arithOp1Exists = false
	arithOp2Exists = false
	inArithExpression = false
	leftIndexExists = false
}

resetBooleanExpression(){
	boolOperatorExists = false
	boolOp1Exists = false
	boolOp2Exists = false
	inBoolExpression = false
}