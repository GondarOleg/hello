<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <H1>REST service</H1>
</head>
<body>
<p><a href="https://travis-ci.org/GondarOleg/hello"><img
        src="https://travis-ci.org/GondarOleg/hello.svg?branch=master" style="max-width:100%;"></a>
<p>REST service hello, performing a search in the database of records by the regular expression.</p>
<p>Supports the following commands:</p>
<ol>
<li> /hello/save - save the test data;</li>
<li> /hello/contacts?nameFilter=<regex> - search using regular expressions;</li>
<li> /hello/find_all - search for all records;</li>
<li> /hello/delete_all - delete all entries.</li>
</ol>
<hr>
<p>1. Technologies used</p>
<ul>
<li>Java</li>
<li>Maven</li>
<li>Spring Boot</li>
<li>Spring JPA</li>
</ul>
<p>2. To Run this project locally</p>
<ul>
<li>1. Download and install Virual Box;</li>
<li>2. Download and install Vagrant</li>
<li>3. Create a folder on a drive;</li>
<li>4. Open a command promt, go to folder;</li>
<li>5. Execute command: vagrant init</li>
<li>6. In a Vagrantfile replace content:</li>
<hr noshade>
<p>config.vm.box = "hashicorp/precise32"</p>
<p>config.vm.provision "shell", path: "vagrant_provision.sh"</p>
<p>config.vm.network "private_network", ip: "192.168.33.10"</p>
<hr noshade>
<li>7. Create a vagrant_provision.sh file under same directory and copy below contents to the file:</li>
<hr noshade>
<p>#!/usr/bin/env bash
<p>sudo apt-get update</p>
<p>sudo apt-get install -y apache2</p>
<p>sudo apt-get install -y tomcat7</p>
<p>sudo apt-get install -y tomcat7-docs</p>
<p>sudo apt-get install -y tomcat7-admin</p>
<p>sudo apt-get install -y git</p>
<p>sudo apt-get install -y maven</p>
<p>sudo apt-get install -y software-properties-common python-software-properties</p>
<p>sudo add-apt-repository ppa:webupd8team/java -y</p>
<p>sudo apt-get update</p>
<p>sudo apt-get install oracle-java8-installer</p>
<p>sudo apt-get install -y oracle-java8-set-default</p>
<p>sudo apt-get install postgres postgresql-client postgresql-contrib phpPgAdmin</p>
<hr noshade>
<li>Set postgres password:</li>
<hr noshade>
<p>$ sudo su </p>
<p>postgres -c psql </p>
<p>postgres</p>
<p>postgres=# </p>
<p>ALTER USER postgres WITH PASSWORD 'password';</p>
<p>postgres=# \q</p>
<hr noshade>
<li>Append the following configuration lines to give access to 192.168.33.0/24 network to the pg_hba.conf:</li>
<hr noshade>
host all all 192.168.33.0/24 trust
<hr noshade>
<li>Clone the GIT repository on local drive (from system command prompt):</li>
<hr noshade>
<p>git clone https://github.com/GondarOleg/hello.git</p>
<hr noshade>
<li>Login to Vagrant: vagrant ssh</li>
<li>Install a hello application as an init.d service:</li> 
<hr noshade>
<p>sudo ln -s /hello/hello.jar /etc/init.d/hello</p>
<hr noshade>
<li>Start service: service hello start</li>
</ul>
</body>
</html>