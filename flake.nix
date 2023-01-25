{
  description = "Scala development shell";

  inputs = {
    nixpkgs.url = github:nixos/nixpkgs/nixpkgs-unstable;
    flake-utils.url = github:numtide/flake-utils;
  };

  outputs = { self, nixpkgs, flake-utils, ... }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        jreOverlay = f: p: {
          jre = p.jdk19;
        };

        pkgs = import nixpkgs {
          inherit system;
          overlays = [ jreOverlay ];
        };
      in
      {
        devShell = pkgs.mkShell {
          name = "scala-dev-shell";

          buildInputs = with pkgs; [
            gnupg
            jekyll
            jre
            sbt
          ];

          JAVA_HOME = "${pkgs.jre}";
        };
      }
    );
}
