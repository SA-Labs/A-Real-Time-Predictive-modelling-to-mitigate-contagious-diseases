function [m,v,Is]=mitigation(tp,i0,r0,t,i,r,d,v,m,Is,k3,Ms,Mi,Cq)


%{
      INPUT
tp=total population
io=infected at t0
r0= recovered at t0
t= time t
i= infected at time t
r= infected at time t
d= days required for recovery
v= vaccination rate
m= medication rate
Is= total issolation possible
k3= recovery after medication
ms= migration rate at susceptible
mi= migration rate at infected
Cq= total population cyber quaantined
%}







%{
      OUTPUT
S= susceptible prediction
I= infected prediction
R= recovered prediction
H= hrs
A= alpha
B= bta
k1= transmission rate
k2= recovery rate
tmax= time at max I

%}





% function starts
%disp('function started');
%step1= finding hrs

k2=1/d;
s0=tp-i0-r0;
s=tp-i-r;
R=(log(s0/s))/(r-r0);
hrs=tp*R;


% step2 =finding k1 and k2

% finding error using initial k2
a=r0;
b=r;
tps=num2str(tp);
s0s=num2str(s0);
Rs=num2str(R);
r0s=num2str(r0);
k2s=num2str(k2);
fstr =strcat('1/((',tps,'-x-(',s0s,'*(2.71828^(-',Rs,'*(x-',r0s,')))))*',k2s,')');
f=vectorize(inline(fstr,0));
% Seven-point integration scheme so zeta_1 to zeta_7
zeta=[-0.9491079123; -0.7415311855; -0.4058451513; 0.0;
0.4058451513; 0.7415311855; 0.9491079123];
% Weighting coefficients
w=[0.1294849661; 0.2797053914; 0.3818300505; 0.4179591836;
0.3818300505; 0.2797053914; 0.1294849661];
% Index for the seven points
Index=1:7; 
% Gauss Integration
I=(b-a)/2*sum(w(Index).*f((b-a).*(zeta(Index)+1)/2+a));
% Display the result
 diff=t-I; % error founf 
 
 
if diff>0
  for t1=0:+0.001:10
      df=d+t1;
    
      k2=1/df;
   k2s=num2str(k2);
fstr =strcat('1/((',tps,'-x-(',s0s,'*(2.71828^(-',Rs,'*(x-',r0s,')))))*',k2s,')');   
    f=vectorize(inline(fstr,0));
% Seven-point integration scheme so zeta_1 to zeta_7
zeta=[-0.9491079123; -0.7415311855; -0.4058451513; 0.0;
0.4058451513; 0.7415311855; 0.9491079123];
% Weighting coefficients
w=[0.1294849661; 0.2797053914; 0.3818300505; 0.4179591836;
0.3818300505; 0.2797053914; 0.1294849661];
% Index for the seven points
Index=1:7; 
% Gauss Integration
I=(b-a)/2*sum(w(Index).*f((b-a).*(zeta(Index)+1)/2+a));
% Display the result
 
 diff=t-I;
     if(diff<0.001)
     break;
     end
  end
end

if diff<0
     
  for t1=0:+0.001:100
      df=d-t1;
  
      k2=1/df;
   k2s=num2str(k2);
fstr =strcat('1/((',tps,'-x-(',s0s,'*(2.71828^(-',Rs,'*(x-',r0s,')))))*',k2s,')');   
    f=vectorize(inline(fstr,0));
% Seven-point integration scheme so zeta_1 to zeta_7
zeta=[-0.9491079123; -0.7415311855; -0.4058451513; 0.0;
0.4058451513; 0.7415311855; 0.9491079123];
% Weighting coefficients
w=[0.1294849661; 0.2797053914; 0.3818300505; 0.4179591836;
0.3818300505; 0.2797053914; 0.1294849661];
% Index for the seven points
Index=1:7; 
% Gauss Integration
I=(b-a)/2*sum(w(Index).*f((b-a).*(zeta(Index)+1)/2+a));
% Display the result
 
 diff=t-I;

 if(diff>-0.001 )
     break;
 end
  end 
end
 

d=df;
k2=1/d;
k1=k2*R;
e='2.7183';
%disp('k1 and k2 found');
%k1 and k2 found
mbol=0;
vbol=0;
isbol=0;
if(m==0)
    
       mbol=1;
end;
if(v==0)
       vbol=1;
end;    
if(Is==0)
      isbol=1; 
end




%step 3= prediction
while hrs>1
    if(mbol==1)  
    m=m+(i0/100);
    end;
    if(vbol==1)
    v=v+(s0/100);    
    end;
    if(isbol==1)
    Is=Is+(i0/100);    
    end;    
t_predict=50;
k1s=num2str(k1);
k2s=num2str(k2);
k3s=num2str(k3);
vs=num2str(v);
ms=num2str(m);
Ms=num2str(Ms);
Mi=num2str(Mi);
Iss=num2str(Is);


bta= strcat('(',k1s,'.*(x(1)-(x(5)+x(6))).*(x(2)-x(7)))');
gamma= strcat('((',k2s,'.*(x(2)-x(8)))+ (x(8).*',k3s,'))');
h1=strcat('(',k1s,'.*(x(2)-',Iss,').*((',k2s,'.*(',bta,'-',gamma,'))+(',ms,'.*(',k3s,'-',k2s,'))))');
h2=strcat('((((x(2)-(',ms,'.*t)).*',k2s,')+(',ms,'.*t.*',k3s,')-',Mi,').*(',k1s,'.*(',bta,'-',gamma,')))');
h3=strcat('((',k1s,'.*(x(2)-',Iss,')).*(',k1s,'.*(x(2)-',Iss,')))');
h=strcat('((',h1,'-',h2,')./',h3,')+',vs);
th0 =((i0*k2)/(k1*(i0-Is)))+Cq;


g=inline(strcat('[-',bta,'+',Ms,';',bta,'-',gamma,'+',Mi,';+',gamma,';',h,';',vs,';','0',';','0',';',ms,'-(x(8).*',k3s,');]'),'t','x');
disp(strcat('[-',bta,'+',Ms,';',bta,'-',gamma,'+',Mi,';+',gamma,';',h,';',vs,';','0',';','0',';',ms,'-(x(8).*',k3s,');]'));
[t xa]=ode45(g,[0 t_predict],[s0 i0 r0 th0 0 Cq Is 0])


%{
bta= strcat('(',k1s,'.*(x(1)).*(x(2)))');
gamma= strcat('(',k2s,'.*(x(2)))');

g=inline(strcat('[-',bta,'+',Ms,';',bta,'-',gamma,'+',Mi,';+',gamma,';]'),'t','x');
[t xa]=ode45(g,[0 t_predict],[s0 i0 r0])
%}



%plot(t,xa(:,1),'r');
%hold;
%plot(t,xa(:,2),'k');
%plot(t,xa(:,3),'b');
%plot(t,xa(:,4),'r');




%S=xa(:,1);
%I=xa(:,2);
%R=xa(:,3);

%prediction done



%step 4= factors

x=t;
y=xa(:,2);
%plot(x,y);

%tmax
indexmax = find(max(y) == y);
xmax = x(indexmax);
%disp('time at which i is max');
%disp(xmax);
tmax=xmax;

%alpha
ymax = y(indexmax);

imax=ymax;
alpha=(imax-i0)/xmax;
A=alpha;
%disp('growing rate');
%disp(alpha);

%beta
tf=2*xmax;
y= xa(:,2);
ifinal=y(2*indexmax);
beta=(ymax-ifinal)/xmax;
%disp('decaying rate');
%disp(beta);
B=beta;

%hrs

y=xa(:,4);
ymax = y(indexmax);
hrs=tp/ymax;
%disp('hrs=');
%disp(hrs);
end
disp('m');
disp(m);
disp('v');
disp(v);
disp('Is');
disp(Is);

end
