import java.io.*;
import java.io.FilenameFilter;
import java.lang.reflect.*;
import java.lang.annotation.*;
import com.itextpdf.kernel.pdf.*; 
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.property.*;
import java.util.*;
import java.lang.reflect.*;
public class Service
{
private Class serviceClass;
private Method service;
private String methodReturnType;
private String parameterName;
public Service()
{
this.serviceClass=null;
this.service=null;
this.methodReturnType=" ";
this.parameterName="";
}
public void setParameterName(String parameterName)
{
this.parameterName=parameterName;
}
public String getParameterName()
{
return this.parameterName;
}
public void setMethodReturnType(String methodReturnType)
{
this.methodReturnType=methodReturnType;
}
public String getMethodReturnType()
{
return this.methodReturnType;
}
public void setMethod(Method service)
{
this.service=service;
}
public Method getMethod()
{
return this.service;
}
public Class getServiceClass()
{           
return this.serviceClass;
}
public void setClass(Class serviceClass)
{                 
this.serviceClass=serviceClass;
}
public static void main(String gg[])
{
try
{
HashMap<Integer, Service> hash=new HashMap<Integer,Service>();
int j=0;
if(gg.length>2)
{
System.out.println("only two command line arugments are required");
return;
}
if(gg.length<2)
{
System.out.println("two command line arugments are required");
return;
}
File f=new File(gg[0]);
FilenameFilter filenameFilter = (file, s) ->true;
File[] list = f.listFiles(filenameFilter);
File ds=null;
String cc=null;
for(File file : list) 
{
if(file.getName().endsWith(".java")) 
{
RandomAccessFile r=new RandomAccessFile(file,"r");
String a=r.readLine();
a=a.replace("package","");
a=a.replace(";","");
a=a.replace(" ","");
String c=file.getName();
c=c.replace(".java"," ");
c=c.replace(" ","");
Class clazz=Class.forName(a+"."+c);
String url=null;
Service service=null;
Method method[]=clazz.getDeclaredMethods();
for(int k=0;k<method.length;k++) 
{
service=new Service();
service.setMethod(method[k]);
service.setMethodReturnType(method[k].getReturnType().getName());
Class parameters[]=method[k].getParameterTypes();
String pName="";
for(int x=0;x<parameters.length;x++)
{
pName=pName+parameters[x].getName();
pName=pName+",";
}
service.setParameterName(pName);
//method return type property should be added
String parameterName=null;
service.setClass(clazz);
hash.put(j,service);
j++;
} //for loop of methods
} //if condition file ends with java
} // file loop
//here itext pdf code will come 
//where we will take out each service object print it on the pdf in tabular form
String dest = gg[1];   
PdfWriter writer = new PdfWriter(dest);       
// Creating a PdfDocument object      
PdfDocument pdf = new PdfDocument(writer);                  
// Creating a Document object       
Document doc = new Document(pdf);                       
// Creating a table       
float [] pointColumnWidths = {150F, 150F};   
Table table = new Table(pointColumnWidths);    
// Adding cells to the table       
for(int i=0;i<hash.size();i++)
{
Service service=hash.get(i);
Class c=service.getServiceClass();
String className=c.getName();
String methodName=service.getMethod().getName();
table.addCell(new Cell().add(new Paragraph("Class-Name")));       
table.addCell(new Cell().add(new Paragraph(className)));       
table.addCell(new Cell().add(new Paragraph("Method Name")));       
table.addCell(new Cell().add(new Paragraph(methodName)));       
table.addCell(new Cell().add(new Paragraph("Method Parameter Names")));       
table.addCell(new Cell().add(new Paragraph(service.getParameterName())));       
table.addCell(new Cell().add(new Paragraph("Method Return type")));       
table.addCell(new Cell().add(new Paragraph(service.getMethodReturnType())));       
table.addCell(new Cell().add(new Paragraph(" ")));       
table.addCell(new Cell().add(new Paragraph(" ")));    
}
// Adding Table to document        
doc.add(table);                  
// Closing the document       
doc.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}