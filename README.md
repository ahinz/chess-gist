Purpose
=================================================

The main point of this app is for me to get familiar with
Play and see how the framework functions but hopefully it
can be useful to you as well!

An app to share chess positions with your friends
=================================================

Like code gists, often times chess player want to be able
to store and save particular chess positions. Just enter a label
and FEN and you're on your way.

Board rendering is all css and unicode so nothing fancy required.

Install
=================================================

Make sure you have Vagrant and Ansible and the start the VM:

```
vagrant up
```

Since this is a dev toy, there isn't a unicorn/supervisord
script. To get the app running just issued the play command:

```
vagrant ssh -c "cd /vagrant && play run"
```

Head over to `http://10.0.0.10:9000/` and start entering some
position! Some example FEN to get you going:

```
rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2
```
