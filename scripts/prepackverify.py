#!/usr/bin/env python
# -*- coding: UTF-8 -*-

import os
import argparse

def sub_verify(args):
    os.system("java -jar verification.jar --verify --install-pkg-root=" + args.install_pkg_root + " --expect-info=" + args.expect_info)
    
def sub_ship(args):
    os.system("java -jar verification.jar --ship --ws-root=" + args.ws_root)
        
def main():
    parser = argparse.ArgumentParser()
    subparsers = parser.add_subparsers(help="Verify or Ship")
    
    verifycmd = subparsers.add_parser("verify", help="--verify --install-pkg-root=packagefolder --expect-info=datfile")
    #verifycmd.add_argument("--verify", action="store_true", required=True, help="verify")
    verifycmd.add_argument("--install-pkg-root", required=True, help="nstall-pkg-root")
    verifycmd.add_argument("--expect-info", nargs="?", required=True, help="expect-info")
    verifycmd.set_defaults(func=sub_verify)
    
    shipcmd = subparsers.add_parser("ship", help="--ship --ws-root=prepackworkspace")
    #shipcmd.add_argument("--ship", action="store_true", required=True, help="ship")
    shipcmd.add_argument("--ws-root", nargs="?", required=True, help="ws-root")
    shipcmd.set_defaults(func=sub_ship)

    args = parser.parse_args()
    args.func(args)

if __name__ == '__main__':
    main()
    
