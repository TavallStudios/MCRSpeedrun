#!/bin/sh
set -eu
cd website-frontend
node --version
npm --version
npm ci
npm run build
