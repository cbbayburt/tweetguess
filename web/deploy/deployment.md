# AWS Deployment #

* Instance: t2.micro
* RDS: Mysql 5.7.10 (id:tweetguess | username:root | password:tweetguess)
* OS: Ubuntu 14.10
* JDK 1.8 (JAVA_HOME environment variable set)
* Maven 3.2
* Git


## Updating current OS ##

    sudo apt-get update
    sudo apt-get upgrade


## Creating individual users and configuring ssh connection ##

    sudo adduser username --disabled-password
    sudo su - username
    mkdir ~/.ssh
    chmod 700 ~/.ssh
    touch ~/.ssh/authorized_keys
    chmod 600 ~/.ssh/authorized_keys
    vim ~/.ssh/authorized_keys
    -> paste & save
    sudo service ssh restart

* Deleting user

        sudo userdel -r tweetguess


## Creating application users and getting source codes ##

    sudo adduser tweetguess --disabled-login
    sudo su - tweetguess
    ssh-keygen
    ps -e | grep [s]sh-agent (if null then run below command)
    ssh-agent /bin/bash
    ssh-add ~/.ssh/id_rsa
    ssh-add -l
    cat ~/.ssh/id_rsa.pub
    -> copy & paste to bitbucket
    mkdir ~/git
    cd ~/git
    git clone git@bitbucket.org:aykutakin/tweetguess.git


## Building application ##

    sudo su - tweetguess
    cd ~/git/tweetguess/web
    mvn clean package install
    cp ~/git/tweetguess/web/target/tweetguess.war ~/web
    cd ~/git/tweetguess/batch
    mvn clean package
    cp ~/git/tweetguess/batch/target/tweetguess-batch.jar ~/batch

* NEW FILE: ~/web/tweetguess.conf

    ** NOTE: The conf file should be situated next to the jar file and have the same name. Spring will autodetect this file.

        JAVA_OPTS="-Dspring.profiles.active=production -Dlog4j.configurationFile=./log4j2.xml"
        RUN_ARGS=--spring.config.location=./application.yaml

** Setting file owners if build by another user: **

    sudo chown tweetguess:tweetguess tweetguess.war


## Forwarding port 8081 to 80 ##

** Inserting iptables rule: **

    sudo iptables -t nat -I PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8081

** Listing iptables rules for nat table: **

    sudo iptables -t nat -L -v

** Persisting iptables rules: **

* NEW FILE: /etc/network/if-post-down.d/iptablessave:

        #!/bin/sh
        iptables-save -t nat > /etc/iptables.rules
        if [ -f /etc/iptables.downrules ]; then
            iptables-restore < /etc/iptables.downrules
        fi
        exit 0

* NEW FILE: /etc/network/if-pre-up.d/iptablesload:

        #!/bin/sh
        iptables-restore < /etc/iptables.rules
        exit 0


## MySql character set configuration ##

* APPEND FILE: /etc/mysql/my.cnf

        !includedir /etc/mysql/conf.d/
        !includedir /etc/mysql/mysql.conf.d/

        [client]
        default-character-set=utf8

        [mysql]
        default-character-set=utf8

        [mysqld]
        collation-server=utf8mb4_general_ci
        init-connect='SET NAMES utf8mb4'
        character-set-server=utf8mb4

* Then restart the server

        sudo service mysql restart


## Executing DB scripts ##

** Creating database **

    mysql -u root -p < db/database_create.sql
    mysql -u tweetguess -p < db/tables_create.sql


## Deploying tweetguess services for Ubuntu 15.04 or later ##

** Installing systemd services: **

* NEW FILE: /etc/systemd/system/tweetguess.service

        [Unit]
        Description=tweetguess-web
        After=syslog.target

        [Service]
        ExecStart=/home/tweetguess/web/tweetguess.war
        User=tweetguess
        Restart=always

        [Install]
        WantedBy=multi-user.target

* Enable services

        sudo systemctl enable tweetguess.service

* Starting services manually

        sudo service tweetguess start

* Uninstalling Services #
    
        sudo service tweetguess stop
        sudo systemctl disable tweetguess.service
        sudo rm /etc/systemd/system/tweetguess.service


# Links #

* http://docs.spring.io/spring-boot/docs/1.3.0.M2/reference/html/deployment-install.html
* https://www.digitalocean.com/community/tutorials/how-to-configure-a-linux-service-to-start-automatically-after-a-crash-or-reboot-part-1-practical-examples
* https://wiki.ubuntu.com/systemd


# tweetguess Logger Hierarchy and Appenders #

                           ROOT:warn
                           (CA:inherit, CRFA:inherit, ERRORFA:error)
    _______________________|____________________________________
    |                           |                              |
    ASPECT_LOGGER:info          com.dedeler.tweetguess:info    org:info
    (ASPECT_LOGGER:inherit)
