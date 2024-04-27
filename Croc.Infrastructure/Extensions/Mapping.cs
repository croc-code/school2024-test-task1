using Croc.Domain;
using Croc.Infrastructure.Contracts;

namespace Croc.Infrastructure.Extensions;

public static class Mapping
{
    public static Order ToDomainOrder(this JsonOrder src)
    {
        return new Order()
        {
            UserId = src.UserId,
            OrderedAt = src.OrderedAt,
            Status = (Status)(int)src.Status,
            Total = Convert.ToDecimal(src.Total.Replace('.', ','))
        };
    }
}