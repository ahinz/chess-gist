# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  # All Vagrant configuration is done here. The most common configuration
  # options are documented and commented below. For a complete reference,
  # please see the online documentation at vagrantup.com.

  # Every Vagrant virtual environment requires a box to build off of.
  config.vm.box = "precise64"

  config.vm.synced_folder ".", "/vagrant", type: "rsync"

  # The url from where the 'config.vm.box' box will be fetched if it
  # doesn't already exist on the user's system.
  config.vm.box_url = "http://files.vagrantup.com/precise64.box"

  config.vm.network "private_network", ip: "10.0.0.10"

  config.vm.network :forwarded_port, guest: 8000, host: 8000

  config.vm.provision :ansible do |ansible|
    ansible.playbook = "provisioning/playbook.yml"
    ansible.sudo = true
  end

  config.vm.provider :virtualbox do |vb|
    vb.memory = 3072
    vb.customize ["modifyvm", :id, "--natdnshostresolver1", "on"]
  end
end
