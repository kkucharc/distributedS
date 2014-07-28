  # Generate first n Fibonacci numbers
# Author: shanmugs1@gmail.com

use strict;

my $n = shift || die "Usage: $0 <n>\n";

my $n1 = 1;
my $n2 = 1;

#print "$n1\n";
while ($n > 1) {
    print "$n2\n";
    my $t = $n1 + $n2;
    $n1   = $n2;
    $n2   = $t;
    $n--;
}
