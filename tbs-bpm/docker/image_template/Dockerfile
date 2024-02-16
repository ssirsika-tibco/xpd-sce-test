# Copyright (c) 2004-$$COPYRIGHT_END_YEAR$$. Cloud Software Group, Inc. All Rights Reserved.

# Installer stage:
FROM centos:7.6.1810@sha256:6ae4cddb2b37f889afd576a17a5286b311dcbf10a904409670827f6f9b50065e as installer
LABEL maintainer="TIBCO Software Inc" \
      image="bpm-studio-installation" \
      copyright="(c) Cloud Software Group, Inc 2004 - $$COPYRIGHT_END_YEAR$$. All rights reserved."

# install required software
RUN yum -y -q install unzip  && \
    groupadd -r studio && useradd -r -m -g studio studio  && \
    mkdir -p /opt/TIBCO/studio-bpm /tmp/tibco/product /tmp/tibco/hf  && \
    chown -R studio:studio /opt/TIBCO/studio-bpm /tmp/tibco/

USER studio:studio


# copy and unzip installer into /tmp/tibco
COPY TIB_business-studio-bpm-edition_?.?.?_linux*.zip /tmp/tibco/product/TIB_business-studio-bpm-edition.zip
COPY TIB_business-studio-bpm-edition_?.?.?_HF-*.zip /tmp/tibco/hf/TIB_business-studio-bpm-edition-hf.zip

#copy silent files to /tmp/tibco/product and /tmp/tibco/hf
COPY docker-studio.silent /tmp/tibco/product/
COPY docker-studio-hf.silent /tmp/tibco/hf/

# change the working directory to /tmp/tibco/product
WORKDIR /tmp/tibco/product

# unzip the installer and install the studio
RUN unzip -q TIB_business-studio-bpm-edition.zip && \
     ./TIBCOUniversalInstaller-lnx-x86-64.bin -silent -V responseFile="docker-studio.silent"

# install hotfix if it exists
RUN if [ -f /tmp/tibco/hf/TIB_business-studio-bpm-edition-hf.zip ]; then \
        cd /tmp/tibco/hf && \
        unzip -q TIB_business-studio-bpm-edition-hf.zip && \
        cp ./../product/TIBCOUniversalInstaller-lnx-x86-64.bin . && \
        ./TIBCOUniversalInstaller-lnx-x86-64.bin -silent -V responseFile="docker-studio-hf.silent" && \
        cd /tmp/tibco/product && \
        echo "Hotfix installed."; \
    else \
        echo "No hotfix found."; \   
    fi

# initialize the eclipse configuration - to improve start-up performance
WORKDIR /opt/TIBCO/studio-bpm/studio/$$STUDIO_PRODUCT_HOME_VERSION$$/eclipse
RUN ./TIBCOBusinessStudio -initialize -noSplash -clean

# start a fresh image - in order to reduce final size. Production stage:
FROM centos:7.6.1810@sha256:6ae4cddb2b37f889afd576a17a5286b311dcbf10a904409670827f6f9b50065e as production
LABEL maintainer="TIBCO Software Inc" \
      image="Production" \
      copyright="(c) Cloud Software Group, Inc 2004 - $$COPYRIGHT_END_YEAR$$. All rights reserved."

# install required software and create studio user
RUN yum -y -q install xorg-x11-server-Xvfb.x86_64 && \
    yum -y -q install install /usr/lib64/libstdc++.so.5 && \
    yum -y -q install gtk3 && \
    yum -y -q install /usr/lib64/libXtst.so.6 && \
    groupadd -r studio && useradd -r -m -g studio studio  && \
    mkdir -p /opt/TIBCO/studio-bpm  && \
    chown -R studio:studio /opt/TIBCO/studio-bpm && \
    dbus-uuidgen --ensure

# WORKSPACE_DIR = the folder in which the studio workspace is to be created
# PROJECTS_DIR  = the folder in which the project to be imported is located
# RASCS_DIR   = the folder in which the generated deployment artifacts (RASCs) are to be written
ENV WORKSPACE_DIR=/workspace \
    PROJECTS_DIR=/projects \
    RASCS_DIR=/rascs

RUN mkdir -p "$WORKSPACE_DIR" && chown -R studio:studio "$WORKSPACE_DIR" && chmod 777 "$WORKSPACE_DIR" && \
    mkdir -p "$PROJECTS_DIR"  && chown -R studio:studio "$PROJECTS_DIR"  && chmod 777 "$PROJECTS_DIR"   && \
    mkdir -p "$RASCS_DIR"   && chown -R studio:studio "$RASCS_DIR"   && chmod 777 "$RASCS_DIR"

# allow user to supply their own folders (workspace is optional)
VOLUME $WORKSPACE_DIR $PROJECTS_DIR $RASCS_DIR

# copy studio installation from previous image
COPY --from=installer --chown=studio:studio /opt/TIBCO/studio-bpm /opt/TIBCO/studio-bpm

# copy the ant script with the generateRasc command
COPY --chown=studio:studio build.xml entrypoint.sh deployrascs.sh /opt/TIBCO/studio-bpm/studio/$$STUDIO_PRODUCT_HOME_VERSION$$/eclipse/

USER studio:studio
WORKDIR /opt/TIBCO/studio-bpm/studio/$$STUDIO_PRODUCT_HOME_VERSION$$/eclipse


# run the ant script
ENTRYPOINT [ "sh", "./entrypoint.sh" ]